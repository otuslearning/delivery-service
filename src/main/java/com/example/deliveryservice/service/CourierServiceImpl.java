package com.example.deliveryservice.service;

import com.example.deliveryservice.api.CreateCourierRequestDto;
import com.example.deliveryservice.api.CourierService;
import com.example.deliveryservice.converter.CourierConverter;
import com.example.deliveryservice.domain.Courier;
import com.example.deliveryservice.domain.Delivery;
import com.example.deliveryservice.exception.CourierNotAvailableException;
import com.example.deliveryservice.repository.CourierRepository;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourierServiceImpl implements CourierService {
    private static final Logger log = LoggerFactory.getLogger(CourierServiceImpl.class);
    private final CourierRepository courierRepository;
    private final CourierConverter courierConverter;
    private static final String COURIER_CANT_NOT_RESERVED = "Courier can't reserved on date: %s";
    @Override
    @Transactional
    public void addCourier(CreateCourierRequestDto dto) {
        Courier courier = courierConverter.convert(dto);
        courierRepository.save(courier);
    }

    @Override
    @Transactional
    public void reserveDelivery(String orderGuid, String date) {
        List<Courier> availableCouriers = findAvailableCouriers(date);
        if (availableCouriers.isEmpty()) {
            throw new CourierNotAvailableException(
                    String.format(COURIER_CANT_NOT_RESERVED, date)
            );
        }
        Courier courier = availableCouriers.get(0);
        courier.getDeliveries().add(createDelivery(courier, orderGuid, date));
        log.info("courier: {}", courier);
        courierRepository.save(courier);
    }

    private Delivery createDelivery(Courier courier, String orderGuid, String date) {
        Delivery delivery = new Delivery();
        delivery.setCourier(courier);
        delivery.setOrderGuid(orderGuid);
        delivery.setDate(date);
        return delivery;
    }

    private List<Courier> findAvailableCouriers(String date) {
        return courierRepository.findAll((root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Delivery> delivery = subquery.from(Delivery.class);
            subquery.select(delivery.get("courier").get("id"));
            subquery.where(criteriaBuilder.equal(delivery.get("date"), date));
            return criteriaBuilder.not(root.get("id").in(subquery));
        });
    }

}

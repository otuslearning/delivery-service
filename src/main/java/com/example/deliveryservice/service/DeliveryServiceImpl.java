package com.example.deliveryservice.service;

import com.example.deliveryservice.api.DeliveryService;
import com.example.deliveryservice.domain.Delivery;
import com.example.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    @Override
    @Transactional
    public void deleteDeliveryForOrder(String orderGuid) {
        Delivery delivery = deliveryRepository.findByOrderGuid(orderGuid).orElseThrow();
        deliveryRepository.delete(delivery);
        log.info("Delete delivery: {} ", delivery);
    }
}

package com.example.deliveryservice.repository;

import com.example.deliveryservice.domain.Delivery;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Delivery> findByOrderGuid(String orderGuid);
}

package com.example.deliveryservice.repository;

import com.example.deliveryservice.domain.Courier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends CrudRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

}

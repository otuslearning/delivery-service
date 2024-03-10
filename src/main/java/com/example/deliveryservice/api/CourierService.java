package com.example.deliveryservice.api;

import com.example.deliveryservice.exception.CourierNotAvailableException;

public interface CourierService {
    void addCourier(CreateCourierRequestDto dto);
    void reserveDelivery(String orderGuid, String date) throws CourierNotAvailableException;
}

package com.example.deliveryservice.converter;

import com.example.deliveryservice.api.CreateCourierRequestDto;
import com.example.deliveryservice.domain.Courier;
import org.springframework.stereotype.Component;


@Component
public class CourierConverter {
    public Courier convert(CreateCourierRequestDto dto) {
        Courier courier = new Courier();
        courier.setName(dto.getName());
        return courier;
    }

}

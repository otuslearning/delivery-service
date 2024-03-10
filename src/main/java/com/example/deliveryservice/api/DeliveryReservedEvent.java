package com.example.deliveryservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryReservedEvent {
    private String orderGuid;
}

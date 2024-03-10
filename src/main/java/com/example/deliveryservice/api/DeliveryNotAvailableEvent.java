package com.example.deliveryservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryNotAvailableEvent {
    private String date;
    private String orderGuid;
}

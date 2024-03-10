package com.example.deliveryservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservedEvent {
    private String orderGuid;
    private String productGuid;
    private String date;
}

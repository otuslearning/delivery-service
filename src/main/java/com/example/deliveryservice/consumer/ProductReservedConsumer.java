package com.example.deliveryservice.consumer;

import com.example.deliveryservice.api.DeliveryNotAvailableEvent;
import com.example.deliveryservice.api.ProductReservedEvent;
import com.example.deliveryservice.api.CourierService;
import com.example.deliveryservice.api.DeliveryReservedEvent;
import com.example.deliveryservice.exception.CourierNotAvailableException;
import com.example.deliveryservice.producer.DeliveryProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReservedConsumer {

    private final ObjectMapper mapper;
    private final CourierService courierService;
    private final DeliveryProducer deliveryProducer;
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.product-reserved}")
    public void consume(String message) {
        ProductReservedEvent productReservedEvent = null;
        try {
            productReservedEvent = mapper.readValue(message, ProductReservedEvent.class);
            courierService.reserveDelivery(productReservedEvent.getOrderGuid(), productReservedEvent.getDate());
            DeliveryReservedEvent deliveryReservedEvent = DeliveryReservedEvent.builder()
                    .orderGuid(productReservedEvent.getOrderGuid())
                    .build();
            deliveryProducer.reservedMessage(deliveryReservedEvent);
        } catch (JsonProcessingException e) {
            log.error("Error parsing message: {}", message, e);
        } catch (CourierNotAvailableException e) {
            log.error("Error processing message: {}. {}", message, e.getMessage());
            if(productReservedEvent != null) {
                DeliveryNotAvailableEvent productEvent = DeliveryNotAvailableEvent.builder()
                        .date(productReservedEvent.getDate())
                        .orderGuid(productReservedEvent.getOrderGuid())
                        .build();
                deliveryProducer.notReservedMessage(productEvent);
            }
        }

    }
}

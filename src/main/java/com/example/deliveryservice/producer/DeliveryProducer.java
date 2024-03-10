package com.example.deliveryservice.producer;

import com.example.deliveryservice.api.DeliveryNotAvailableEvent;
import com.example.deliveryservice.api.DeliveryReservedEvent;
import com.example.deliveryservice.exception.ConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryProducer {
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void reservedMessage(DeliveryReservedEvent message){
        Assert.notNull(message, "message mustn't be null");
        try {
            kafkaTemplate.send("delivery-reserved", mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Error convert and send product reserved event: {}", message, e);
            throw new ConvertException(e.getMessage());
        }
    }

    public void notReservedMessage(DeliveryNotAvailableEvent message) {
        Assert.notNull(message, "message mustn't be null");
        try {
            kafkaTemplate.send("delivery-not-reserved", mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Error convert and send product not reserved event: {}", message, e);
            throw new ConvertException(e.getMessage());
        }
    }
}

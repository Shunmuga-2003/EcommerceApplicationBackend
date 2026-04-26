package com.Ecommerce.App.service;

import com.Ecommerce.App.kafka.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final TwilioSmsService twilioSmsService;

//    @KafkaListener(
//            topics = "order-placed",
//            groupId = "notification-group"
//    )
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Notification: order {} placed",
                event.getOrderId());

        // SMS disabled for now
        // twilioSmsService.sendSms(event.getPhone(), message);
    }
}
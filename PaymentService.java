package com.Ecommerce.App.service;

import com.Ecommerce.App.entity.Order;
import com.Ecommerce.App.entity.Payment;
import com.Ecommerce.App.kafka.OrderPlacedEvent;
import com.Ecommerce.App.repository.OrderRepository;
import com.Ecommerce.App.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository   orderRepository;
//
//    @KafkaListener(
//            topics = "order-placed",
//            groupId = "payment-group"
//    )
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Payment: processing order {}",
                event.getOrderId());

        Order order = orderRepository
                .findById(event.getOrderId())
                .orElse(null);

        if (order == null) {
            log.error("Order not found: {}",
                    event.getOrderId());
            return;
        }

        Payment payment = Payment.builder()
                .order(order)
                .amount(event.getTotalAmount())
                .status("SUCCESS")
                .method(order.getPaymentMethod())
                .transactionId("TXN" +
                        System.currentTimeMillis())
                .build();

        paymentRepository.save(payment);
        log.info("Payment saved for order: {}",
                event.getOrderId());
    }
}
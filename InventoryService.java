package com.Ecommerce.App.service;

import com.Ecommerce.App.kafka.OrderPlacedEvent;
import com.Ecommerce.App.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
//
//    @KafkaListener(
//            topics = "order-placed",
//            groupId = "inventory-group"
//    )
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Inventory: processing order {}",
                event.getOrderId());
        for (var item : event.getItems()) {
            int updated = inventoryRepository.deductStock(
                    item.getProductId(),
                    item.getQuantity()
            );
            if (updated == 0) {
                log.error("Stock deduct failed for: {}",
                        item.getProductId());
            }
        }
    }
}
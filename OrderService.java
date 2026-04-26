package com.Ecommerce.App.service;

import com.Ecommerce.App.dto.OrderItemRequestDTO;
import com.Ecommerce.App.dto.OrderItemResponseDTO;
import com.Ecommerce.App.dto.OrderRequestDTO;
import com.Ecommerce.App.dto.OrderResponseDTO;
import com.Ecommerce.App.entity.*;
import com.Ecommerce.App.kafka.OrderPlacedEvent;
import com.Ecommerce.App.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository     orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository   productRepository;
    private final UserRepository      userRepository;
    private final InventoryRepository inventoryRepository;
   // private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemReq : request.getItems()) {

            Product product = productRepository
                    .findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found: "
                                    + itemReq.getProductId()));

            Inventory inventory = inventoryRepository
                    .findByProductId(product.getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Inventory not found"));

            int available = inventory.getQuantity()
                    - inventory.getReservedQty();

            if (available < itemReq.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for: "
                                + product.getName());
            }

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(
                            itemReq.getQuantity()));

            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status("PLACED")
                .paymentMethod(request.getPaymentMethod())
                .deliveryAddress(request.getDeliveryAddress())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        OrderPlacedEvent event = OrderPlacedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(user.getId())
                .phone(user.getPhone())
                .totalAmount(totalAmount)
                .items(request.getItems())
                .build();

        //kafkaTemplate.send("order-placed", event);
        log.info("Order placed: {}", savedOrder.getId());

        return mapToDTO(savedOrder, orderItems);
    }

    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(order -> mapToDTO(
                        order, order.getOrderItems()))
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToDTO(Order order,
                                      List<OrderItem> items) {
        List<OrderItemResponseDTO> itemDTOs = items.stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .imageUrl(item.getProduct().getImageUrl())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .items(itemDTOs)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
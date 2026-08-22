package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.service.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    // private final WebClient.Builder webClientBuilder;
    private final InventoryClient inventoryClient;

    @Value("${order.enabled: true}")
    private boolean ordersEnabled;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest, String userId) {
        if (ordersEnabled) {
            log.warn("Order rejected: Service disabled by configuration");
            throw new RuntimeException("Service disabled by configuration");
        }
        log.info("Placing new order");
        Order order = orderMapper.toOrder(orderRequest);
        order.setUserId(userId);
        for (var item : order.getOrderLineItemsList()) {
            String sku = item.getSku();
            Integer quantity = item.getQuantity();
            try {
                // webClientBuilder.build().get()
                //         .uri("http://localhost:8082/api/v1/inventory/reduce/" + sku,
                //                 uriBuilder -> uriBuilder.queryParam("quantity", quantity).build())
                //         .retrieve()
                //         .bodyToMono(Boolean.class)
                //         .block();
                inventoryClient.reduceStock(sku, quantity);
            } catch (Exception ex) {
                log.error("Error when reducing the stock of product {}: {}", sku, ex.getMessage());
                throw new IllegalStateException("No stock available from sku: " + sku);
            }
        }
        order.setOrderNumber(UUID.randomUUID().toString());
        Order placedOrder = orderRepository.save(order);
        log.info("Order placed with id: {}", placedOrder.getId());
        return orderMapper.toOrderResponse(placedOrder);
    }

    @Override
    public List<OrderResponse> getOrders(String userId, boolean isAdmin) {
        List<Order> orders;
        if (isAdmin) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findByUserId(userId);
        }
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    // @Override
    // @Transactional(readOnly = true)
    // public List<OrderResponse> getAllOrders() {
    //     return orderRepository.findAll().stream()
    //             .map(orderMapper::toOrderResponse)
    //             .toList();
    // }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", id)
        );
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
        orderRepository.deleteById(id);
    }
}
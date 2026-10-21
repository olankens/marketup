package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.event.OrderPlacedEvent;
import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.service.client.InventoryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    // private final InventoryClient inventoryClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${order.enabled: true}")
    private boolean ordersEnabled;

    public OrderResponse fallbackMethod(OrderRequest orderRequest, String userId, Throwable throwable) {
        log.error("🛑 Circuit breaker activated. Cause: {}", throwable.getMessage());
        throw new RuntimeException("Inventory service is temporarily unavailable");

    }

    @Override
    @Transactional
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    public OrderResponse placeOrder(OrderRequest orderRequest, String userId) {
        if (!ordersEnabled) {
            log.warn("Order rejected: Service disabled by configuration");
            throw new RuntimeException("Service disabled by configuration");
        }
        log.info("Placing new order");
        Order order = orderMapper.toOrder(orderRequest);
        order.setUserId(userId);
        // for (var item : order.getOrderLineItemsList()) {
        //     String sku = item.getSku();
        //     Integer quantity = item.getQuantity();
        //     try {
        //         inventoryClient.reduceStock(sku, quantity);
        //     } catch (Exception ex) {
        //         log.error("Error when reducing the stock of product {}: {}", sku, ex.getMessage());
        //         throw new IllegalStateException("No stock available from sku: " + sku);
        //     }
        // }
        order.setOrderNumber(UUID.randomUUID().toString());
        Order placedOrder = orderRepository.save(order);
        log.info("Order placed with id: {}", placedOrder.getId());
        List<OrderPlacedEvent.OrderItemEvent> orderItems = order.getOrderLineItemsList().stream()
                .map(item -> new OrderPlacedEvent.OrderItemEvent(
                        item.getSku(), item.getPrice(), item.getQuantity()
                )).toList();
        OrderPlacedEvent event = new OrderPlacedEvent(placedOrder.getOrderNumber(), orderRequest.getEmail(), orderItems);
        rabbitTemplate.convertAndSend("order-events", "order.placed", event);
        log.info("Event sent to RabbitMQ for order: {}", placedOrder.getOrderNumber());
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
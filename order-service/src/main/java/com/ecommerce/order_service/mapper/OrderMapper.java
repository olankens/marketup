package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.OrderLineItemsRequest;
import com.ecommerce.order_service.dto.OrderLineItemsResponse;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest orderRequest);

    OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemsRequest);

    OrderResponse toOrderResponse(Order order);

    OrderLineItemsResponse toOrderLineItemsResponse(OrderLineItems orderLineItems);
}
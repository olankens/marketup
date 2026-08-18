package com.ecommerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private List<OrderLineItemsResponse> orderLineItemsDtoList;
}
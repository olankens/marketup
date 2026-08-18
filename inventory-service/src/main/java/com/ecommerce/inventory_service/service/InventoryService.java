package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    boolean isInStock(String sku, Integer quantity);

    InventoryResponse createInventory(InventoryRequest inventoryRequest);

    List<InventoryResponse> getAllInventory();

    InventoryResponse updateInventory(Long id, InventoryRequest inventoryRequest);

    void deleteInventory(Long id);

    void reduceStock(String sku, Integer quantity);
}
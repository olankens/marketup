package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.dto.InventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        boolean exists = inventoryRepository.existsBySku(inventoryRequest.getSku());
        if (exists) {
            throw new RuntimeException(String.format("Inventory already exists for sku: %s", inventoryRequest.getSku()));
        }
        Inventory inventory = inventoryMapper.toModel(inventoryRequest);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Created inventory for sku: {}", inventory.getSku());
        return inventoryMapper.toResponse(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponse updateInventory(Long id, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Inventory not found for id: %s", id))
        );
        inventory.setSku(inventoryRequest.getSku());
        inventory.setQuantity(inventoryRequest.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Inventory id {} updated", id);
        return inventoryMapper.toResponse(updatedInventory);
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory", "id", id);
        }
        inventoryRepository.deleteById(id);
        log.info("Inventory with id: {} has been deleted", id);
    }

    @Override
    @Transactional
    public void reduceStock(String sku, Integer quantity) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException(String.format("Inventory not found for sku: %s", sku)));
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException(String.format("Stock insufficient for sku: %s", sku));
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
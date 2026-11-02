package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.model.InventoryRequestDTO;
import com.ecommerce.inventory_service.model.InventoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    boolean inStock(String sku, Integer quantity);
    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO);
    void deleteInventory(Long id);
;
}

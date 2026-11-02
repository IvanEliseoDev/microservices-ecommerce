package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.model.InventoryRequestDTO;
import com.ecommerce.inventory_service.model.InventoryResponseDTO;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean inStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {

        boolean exists = inventoryRepository.existsBySku(inventoryRequestDTO.getSku());
        if(exists){
            throw new RuntimeException("El inventario para el SKU: "+ inventoryRequestDTO.getSku() + " ya existe");
        }
        Inventory inventory = inventoryMapper.toInventory(inventoryRequestDTO);
        Inventory inventorySaved = inventoryRepository.save(inventory);
        log.info("El inventario  con el SKU: {} , a sido creado exitosamente", inventoryRequestDTO.getSku());
        return  inventoryMapper.toInventoryResponseDTO(inventorySaved);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toInventoryResponseDTO)
                .toList();
    }

    @Override
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", "id", id));
        inventory.setSku(inventoryRequestDTO.getSku());
        inventory.setQuantity(inventoryRequestDTO.getQuantity());

        Inventory updateInventory = inventoryRepository.save(inventory);

        log.info("Inventario actualizado exitosamente con el ID: {}", id);
        return  inventoryMapper.toInventoryResponseDTO(updateInventory);
    }

    @Override
    public void deleteInventory(Long id) {
        if(!inventoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Inventario", "id", id);
        }
        inventoryRepository.deleteById(id);
        log.info("Inventario eliminado con ID: {}", id);
    }
}

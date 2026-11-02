package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.model.InventoryRequestDTO;
import com.ecommerce.inventory_service.model.InventoryResponseDTO;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/isInStock/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStockInInventory(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity) {
        return inventoryService.inStock(sku, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO createInventory(@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO){
        return  inventoryService.createInventory(inventoryRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDTO updateInventory(@PathVariable("id") Long id, @RequestBody @Valid InventoryRequestDTO inventoryRequestDTO){
        return  inventoryService.updateInventory(id, inventoryRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") Long id){
        inventoryService.deleteInventory(id);
    }

}

package com.ecommerce.inventory_service.mapper;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.model.InventoryRequestDTO;
import com.ecommerce.inventory_service.model.InventoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toInventory(InventoryRequestDTO inventoryRequestDTO);

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponseDTO toInventoryResponseDTO(Inventory inventory);

}

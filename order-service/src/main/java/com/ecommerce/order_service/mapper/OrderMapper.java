package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.OrderLineItemsRequestDTO;
import com.ecommerce.order_service.dto.OrderLineItemsResponseDTO;
import com.ecommerce.order_service.dto.OrderRequestDTO;
import com.ecommerce.order_service.dto.OrderResponseDTO;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Mapeamos explícitamente la lista porque los nombres no coinciden
    // @Mapping(source = "orderLineItemsDtoList", target="orderLineItemsList")
    Order toOrder(OrderRequestDTO orderRequestDTO);

    OrderLineItems toOrderLineItems(OrderLineItemsRequestDTO orderLineItemsRequestDTO);

    // Mapeamos explícitamente la lista de vuelta
    // @Mapping(source = "orderLineItemsList", target = "orderLineItemsDtoList")
    OrderResponseDTO toOrderResponse(Order order);

    // Método auxiliar para la respuesta
    // Aquí NO hace falta @Mapping porque los campos (id, sku, price, quantity) se llaman igual.
    OrderLineItemsResponseDTO toOrderLineItemsResponse(OrderLineItems orderLineItems);


}

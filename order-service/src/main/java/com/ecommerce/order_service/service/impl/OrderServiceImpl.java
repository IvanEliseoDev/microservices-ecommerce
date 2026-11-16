package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.OrderRequestDTO;
import com.ecommerce.order_service.dto.OrderResponseDTO;
import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.service.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

//    private final WebClient.Builder webClientBuilder;

    private final InventoryClient inventoryClient;

    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {

        List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderLineItemsList()
                .stream()
                .map(orderMapper::toOrderLineItems)
                .toList();

        Order order = new Order();

        order.setOrderLineItems(orderLineItems);

        for (var item : order.getOrderLineItems()){
            String sku = item.getSku();
            Integer quantity = item.getQuantity();

            try {
                //Realizamos una llamada sincrona a inventory service
//                String inStock = webClientBuilder
//                        .build()  //construimos la llamada
//                        .put()    //<-indicamos el tipo de llamada
//                        .uri("http://localhost:8082/api/v1/inventory/reduce/" + sku,
//                                uriBuilder -> uriBuilder.queryParam("quantity", quantity).build()) //<- indicamos hacia que uri haremos la llamada
//                        .retrieve() //<- indicamos que debe recibir una respuesta
//                        .bodyToMono(String.class) //<- la respuesta la convertimos en un valor String
//                        .block(); //<- indicamos que este proceso debe de ser bloqueante, no debe de permitir seguir

                inventoryClient.reduceStock(sku, quantity);
            }catch (Exception e) {
                log.error("Error al reducir stock para el producto: {} : {}", sku, e.getMessage());
                throw new IllegalArgumentException("No se pudo procesar la orden: Stock insuficiente o error de inventario");
            }
        }
        order.setOrderNumber(UUID.randomUUID().toString());
        Order savedOrder = orderRepository.save(order);
        log.info("Orden guardada exitosamente con el Id: {}", savedOrder.getId());
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Orden", "id", id));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Orden", "id", id);
        }
        orderRepository.deleteById(id);
        log.info("Orden eliminada con el ID: {}, Exitosamente", id);
    }
}

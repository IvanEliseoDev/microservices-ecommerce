package com.ecommerce.order_service.service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PutExchange;

public interface InventoryClient {

    //Es una ruta que guardamos para utilizar a diferencia de putMapping esta no esta escuchando solo guardando
    @PutExchange("/api/v1/inventory/reduce/{sku}")
    String reduceStock(@PathVariable("sku") String sku, @RequestParam Integer quantity);

}

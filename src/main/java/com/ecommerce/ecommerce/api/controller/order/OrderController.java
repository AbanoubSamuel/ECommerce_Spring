package com.ecommerce.ecommerce.api.controller.order;


import com.ecommerce.ecommerce.api.model.JsonResponse;
import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderServiceImpl orderService;


    public OrderController(OrderServiceImpl orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<JsonResponse<List<Order>>> getOrders(@AuthenticationPrincipal User user)
    {
        List<Order> orders = orderService.getOrders(user);
        JsonResponse<List<Order>> jsonResponse = new JsonResponse<>();

        jsonResponse.setStatus(true);
        jsonResponse.setMessage("Fetched orders successfully");
        jsonResponse.setData(orders);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}

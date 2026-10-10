package com.microservices.order.controller;


import com.microservices.order.model.Order;
import com.microservices.order.dto.OrderResponse;
import com.microservices.order.service.OrderService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-USER-ID") String userId){

        return orderService.createOrder(userId).map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId) {

        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

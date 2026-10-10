package com.microservices.order.controller;


import com.microservices.order.model.CartItem;
import com.microservices.order.dto.CartItemRequest;
import com.microservices.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-USER-ID") String userId, @RequestBody CartItemRequest request ) {
       if(!cartService.addToCart(userId, request))
       {
           return ResponseEntity.badRequest().body("Product out of Stock or User not Found")

                   ;
       }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable Long productId
    ) {

       boolean deleted = cartService.deleteItemFromCart(userId,productId);

       return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> fetchCart(
            @RequestHeader("X-USER-ID") String userId
    ) {

       return ResponseEntity.ok(cartService.getCart(userId));

    }
}

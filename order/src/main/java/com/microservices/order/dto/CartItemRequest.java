package com.microservices.order.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long productid;
    private Integer quantity;
}

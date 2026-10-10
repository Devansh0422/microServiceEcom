package com.microservices.product.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Data
public class ProductRequest {


    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stockQuantity;

    private String category;

    private String imageUrl;
}

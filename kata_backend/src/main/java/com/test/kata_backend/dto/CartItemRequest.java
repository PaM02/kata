package com.test.kata_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemRequest {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}

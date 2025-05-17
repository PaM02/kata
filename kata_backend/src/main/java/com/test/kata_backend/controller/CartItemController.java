package com.test.kata_backend.controller;

import com.test.kata_backend.dto.CartItemRequest;
import com.test.kata_backend.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;
    @PostMapping("/add")
    public ResponseEntity<String> addCartItem(@RequestBody CartItemRequest cartItemRequest){
        return cartItemService.addCartItem(cartItemRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeCartItem(
            @RequestParam("id")  Integer id){
        return cartItemService.removeFromCartItem(id);
    }

    @GetMapping("/items")
    public ResponseEntity<?> retrieveCartItem(@RequestParam("userId")  Integer userId){
        return cartItemService.retrieveAllCartItems(userId);
    }

}

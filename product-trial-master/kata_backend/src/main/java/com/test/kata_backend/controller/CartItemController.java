package com.test.kata_backend.controller;

import com.test.kata_backend.dto.CartItemRequest;
import com.test.kata_backend.service.CartItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "3. Carts", description = "Endpoints pour gérer le panier d'achat de l'utilisateur. Accessible uniquement aux utilisateurs authentifiés.")
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

    @GetMapping("/carts")
    public ResponseEntity<?> retrieveCartItem(@RequestParam("userId")  Integer userId){
        return cartItemService.retrieveAllCartItems(userId);
    }

}

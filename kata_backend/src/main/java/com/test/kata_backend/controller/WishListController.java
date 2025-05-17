package com.test.kata_backend.controller;
import com.test.kata_backend.dto.WishListItemRequest;
import com.test.kata_backend.service.WishListItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/wish")
public class WishListController {

    private final WishListItemService wishListItemService;
    @PostMapping("/add")
    public ResponseEntity<String> addWishListItem(@RequestBody WishListItemRequest wishListItemRequest){
        return wishListItemService.addWishListItem(wishListItemRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeWishListItem(
            @RequestParam("id")  Integer id){
        return wishListItemService.removeFromWishlist(id);
    }

    @GetMapping("/items")
    public ResponseEntity<?> retrieveWishListItem(@RequestParam("userId")  Integer userId){
        return wishListItemService.retrieveAllWishListItems(userId);
    }


}

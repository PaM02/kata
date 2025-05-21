package com.test.kata_backend.service;


import com.test.kata_backend.config.Common;
import com.test.kata_backend.dto.CartItemRequest;
import com.test.kata_backend.entity.CartItemEntity;
import com.test.kata_backend.entity.ProductEntity;
import com.test.kata_backend.entity.UsersEntity;
import com.test.kata_backend.repository.CartItemRepository;
import com.test.kata_backend.repository.ProductRepository;
import com.test.kata_backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {

    private final UsersRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;


    public ResponseEntity<String> addCartItem(CartItemRequest cartItemRequest){

        Optional<UsersEntity> usersEntity = userRepository.findById(cartItemRequest.getUserId());
        Optional<ProductEntity> productEntity = productRepository.findById(cartItemRequest.getProductId());

        if (usersEntity.isEmpty() || productEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Common.UidOrPidNotExist);
        }

        Optional<CartItemEntity> cartItemDBEntity =  cartItemRepository.findByProductIdAndUserId(cartItemRequest.getProductId(),cartItemRequest.getUserId());
        if (cartItemDBEntity.isPresent()){
            cartItemDBEntity.get().setQuantity(cartItemDBEntity.get().getQuantity()+cartItemRequest.getQuantity());
            cartItemRepository.save(cartItemDBEntity.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(Common.addCart);
        }

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProductId(cartItemRequest.getProductId());
        cartItemEntity.setQuantity(cartItemRequest.getQuantity());
        cartItemEntity.setUserId(cartItemRequest.getUserId());
        cartItemRepository.save(cartItemEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Common.addCart);
    }

    public  ResponseEntity<String> removeFromCartItem(Integer id){
      Optional<CartItemEntity> cartItemDBEntity =  cartItemRepository.findById(id);
      if (cartItemDBEntity.isEmpty()){
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Common.productNotExistInCart);
      }
      cartItemRepository.delete(cartItemDBEntity.get());
      return ResponseEntity.ok(Common.productDeletedMessage);
    }

    public ResponseEntity<?> retrieveAllCartItems(Integer userId){
        return cartItemRepository.findByUserId(userId).isEmpty() ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(Common.cartItemIsEmpty) :
                ResponseEntity.ok(cartItemRepository.findByUserId(userId));
    }


}

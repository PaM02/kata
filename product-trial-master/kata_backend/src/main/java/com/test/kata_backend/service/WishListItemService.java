package com.test.kata_backend.service;


import com.test.kata_backend.config.Common;
import com.test.kata_backend.dto.WishListItemRequest;
import com.test.kata_backend.entity.ProductEntity;
import com.test.kata_backend.entity.UsersEntity;
import com.test.kata_backend.entity.WishListItemEntity;
import com.test.kata_backend.repository.ProductRepository;
import com.test.kata_backend.repository.UsersRepository;
import com.test.kata_backend.repository.WishListRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishListItemService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UsersRepository userRepository;

    public ResponseEntity<String> addWishListItem(WishListItemRequest wishListItemRequest){

        Optional<UsersEntity> usersEntity = userRepository.findById(wishListItemRequest.getUserId());
        Optional<ProductEntity> productEntity = productRepository.findById(wishListItemRequest.getProductId());

        if (usersEntity.isEmpty() || productEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Common.UidOrPidNotExist);
        }

       Optional<WishListItemEntity> wishListItemDBEntity =  wishListRepository.findByProductIdAndUserId(wishListItemRequest.getProductId(),wishListItemRequest.getUserId());
        if (wishListItemDBEntity.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Common.productExist);
        }

        WishListItemEntity wishListItemEntity = new WishListItemEntity();
        wishListItemEntity.setUserId(wishListItemRequest.getUserId());
        wishListItemEntity.setProductId(wishListItemRequest.getProductId());
        wishListRepository.save(wishListItemEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Common.addWish);

    }

    public  ResponseEntity<String> removeFromWishlist(Integer id){
        Optional<WishListItemEntity> wishListItemDBEntity =  wishListRepository.findById(id);
        if (wishListItemDBEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Common.productNotExistInWishList);
        }
        wishListRepository.delete(wishListItemDBEntity.get());
      return   ResponseEntity.ok(Common.productDeletedMessage);
    }

    public ResponseEntity<?> retrieveAllWishListItems(Integer userId){
        return wishListRepository.findByUserId(userId).isEmpty() ? ResponseEntity
                .status(HttpStatus.OK).body(Common.wishListIsEmpty) :
                ResponseEntity.ok(wishListRepository.findByUserId(userId));
    }
}

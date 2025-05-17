package com.test.kata_backend.repository;

import com.test.kata_backend.entity.WishListItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishListItemEntity, Integer> {
    Optional<WishListItemEntity> findByProductIdAndUserId(Integer productId, Integer userId);

    List<WishListItemEntity> findByUserId(Integer userId);
}

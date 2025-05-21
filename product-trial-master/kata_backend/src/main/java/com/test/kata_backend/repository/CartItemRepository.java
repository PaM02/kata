package com.test.kata_backend.repository;

import com.test.kata_backend.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByProductIdAndUserId(Integer productId, Integer userId);

    List<CartItemEntity> findByUserId(Integer userId);
}

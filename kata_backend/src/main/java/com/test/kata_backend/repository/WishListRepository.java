package com.test.kata_backend.repository;

import com.test.kata_backend.entity.WishListItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishListItemEntity, Integer> {
}

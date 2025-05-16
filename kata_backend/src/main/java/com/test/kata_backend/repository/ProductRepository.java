package  com.test.kata_backend.repository;

import  com.test.kata_backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<ProductEntity, Integer> {
}

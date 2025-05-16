package  com.test.kata_backend.repository;

import  com.test.kata_backend.entity.UsersEntity;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsernameAndPassword(String username, String password);

    Optional<UsersEntity> findByUsername(String username);

    Optional<UsersEntity> findByEmail(String email);
}
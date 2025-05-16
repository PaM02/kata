package  com.test.kata_backend.service;
import com.test.kata_backend.dto.UserRequest;
import  com.test.kata_backend.entity.UsersEntity;
import  com.test.kata_backend.exceptions.Exceptions;
import  com.test.kata_backend.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUserEntity(@RequestBody UserRequest userRequest) {

        if (usersRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("msg", "username_already_exists"));
        }

        UsersEntity user = UsersEntity.builder()
                .firstname(userRequest.getFirstname())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .build();

        usersRepository.save(user);

        return ResponseEntity.ok(Map.of("msg", "user_created_successfully"));
    }


    public  UsersEntity findUsersEntityById(Long id) {
        return usersRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new Exceptions("Aucun utilisateur avec cet id " + id));
    }


}

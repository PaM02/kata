package com.test.kata_backend.service;

import com.test.kata_backend.config.Common;
import com.test.kata_backend.dto.UserRequest;
import com.test.kata_backend.entity.UsersDocument;
import com.test.kata_backend.entity.UsersEntity;
import com.test.kata_backend.repository.UserSearchRepository;
import com.test.kata_backend.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSearchRepository userSearchRepository;

    public ResponseEntity<?> createUserEntity(@RequestBody UserRequest userRequest) {

        if (usersRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(Common.message, Common.usernameExists));
        }

        if (usersRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(Common.message, Common.emailExists));
        }

        UsersEntity user = UsersEntity.builder()
                .firstname(userRequest.getFirstname())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .build();

        usersRepository.save(user);

        UsersDocument usersDocument = UsersDocument.builder()
                .idSql(user.getId())
                .firstname(userRequest.getFirstname())
                .username(userRequest.getUsername())
                .password(user.getPassword())
                .email(userRequest.getEmail())
                .build();
        userSearchRepository.save(usersDocument);
        return ResponseEntity.ok(Map.of(Common.message, Common.userNameCreated));
    }

    // Chercher dans Elasticsearch par nom
    public List<UsersDocument> searchByName(String userName) {
        return userSearchRepository.findByUsername(userName);
    }
}

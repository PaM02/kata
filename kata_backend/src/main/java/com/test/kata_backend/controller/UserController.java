package com.test.kata_backend.controller;
import com.test.kata_backend.dto.UserRequest;
import com.test.kata_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createUserEntity(@RequestBody UserRequest userRequest){
        return userService.createUserEntity(userRequest);
    }

}

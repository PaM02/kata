package  com.test.kata_backend.controller;
import java.util.HashMap;
import java.util.Map;

import  com.test.kata_backend.dto.LoginRequest;
import com.test.kata_backend.dto.UserRequest;
import com.test.kata_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import  com.test.kata_backend.AuthenticationException;
import  com.test.kata_backend.service.LoginWeb;



@RequestMapping("/auth")
@RestController
@Tag(name = "1. Authentification",description = "Endpoints pour créer un compte utilisateur, se connecter et récupérer un token JWT pour sécuriser les autres requêtes.")
@RequiredArgsConstructor
public class AuthController {

    private final LoginWeb loginCompanyWeb;
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final UserService userService;


    @PostMapping("/account")
    @Operation(summary = "Créer un utilisateur")
    public ResponseEntity<?> createUserEntity(@RequestBody UserRequest userRequest){
        return userService.createUserEntity(userRequest);
    }

    @PostMapping("/token")
    @Operation(summary = "Se connecter à l'application")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            response = loginCompanyWeb.login(loginRequest.getEmail(), loginRequest.getPassword());
            String message = (String) response.get("message");

            if ("Invalid username".equals(message) || "Invalid password".equals(message)) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Internal server error occurred: {}", e.getMessage());
            response.put("message", "InternalServerError");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
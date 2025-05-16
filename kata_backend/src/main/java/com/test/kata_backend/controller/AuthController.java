package  com.test.kata_backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import  com.test.kata_backend.dto.LoginRequest;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import  com.test.kata_backend.AuthenticationException;
import  com.test.kata_backend.service.LoginWeb;
import  com.test.kata_backend.security.JwtService;


@RequestMapping("/authentification")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginWeb loginCompanyWeb;
    private final JwtService jwtService;
    private static final Logger logger = LogManager.getLogger(AuthController.class);


    @PostMapping("/login")
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
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        if (refreshToken != null) {
            try {
                Date expirationDate = jwtService.extractExpiration(refreshToken);
                if (expirationDate.before(new Date())) {
                    response.put("message", "Token expired");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
                String email = jwtService.extractSubject(refreshToken);
                String newToken = jwtService.generateToken(email);

                if (newToken != null) {
                    response.put("new_token", newToken);
                    return ResponseEntity.ok(response);
                }
            } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                response.put("message", "Token expired");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }

        response.put("message", "Token expired");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
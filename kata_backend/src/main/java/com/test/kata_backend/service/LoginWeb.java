package  com.test.kata_backend.service;
import java.util.*;

import  com.test.kata_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import  com.test.kata_backend.AuthenticationException;
import  com.test.kata_backend.entity.UsersEntity;
import  com.test.kata_backend.repository.UsersRepository;


@Service
@RequiredArgsConstructor
public class LoginWeb {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> login(String email, String password) throws AuthenticationException {
        Map<String, Object> response = new HashMap<>();

        Optional<UsersEntity> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            response.put("message", "Invalid email");
            return response;
        }

        UsersEntity user = optionalUser.get();

        if ( !passwordEncoder.matches(password, user.getPassword())) {
            response.put("message", "Invalid password");
            return response;
        }

        processResponse(response, user);
        return response;
    }

    private void processResponse(Map<String, Object> response, UsersEntity user) {

        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        String token = jwtService.generateToken(user.getEmail());

        response.put("user", user);
        response.put("token", token);
        response.put("refresh_token", refreshToken);
    }
}
package  com.test.kata_backend.service;
import java.util.*;

import com.test.kata_backend.config.Common;
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
            response.put(Common.message, Common.invalidEmailMessage);
            return response;
        }

        UsersEntity user = optionalUser.get();

        if ( !passwordEncoder.matches(password, user.getPassword())) {
            response.put(Common.message, Common.invalidPassword);
            return response;
        }

        processResponse(response, user);
        return response;
    }

    private void processResponse(Map<String, Object> response, UsersEntity user) {

        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        String token = jwtService.generateToken(user.getEmail());

        response.put(Common.userString, user);
        response.put(Common.tokenString, token);
        response.put(Common.refreshTokenString, refreshToken);
    }
}
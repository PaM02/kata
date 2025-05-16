package  com.test.kata_backend.security.filters;

import java.util.Collections;
import java.util.List;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import  com.test.kata_backend.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    final private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, java.io.IOException {
        boolean isTokenValid;
        String token = getTokenFromRequestHeader(request.getHeader(AUTHORIZATION_HEADER));

       if (token != null) {
            try {
                isTokenValid = jwtService.isTokenValid(token);
                if (isTokenValid) {
                    Long userId = Long.valueOf(jwtService.extractSubject(token));

                    List<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_USER")
                    );

                    Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null,
                           authorities );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (SignatureException e) {
                throw new SignatureException("JWT Token signature invalid");
            } catch (MalformedJwtException e) {
                throw new MalformedJwtException("JWT Token malformed");
            } catch (ExpiredJwtException e) {
                throw new ExpiredJwtException(null, null, "JWT Token has expired");
            } catch (UnsupportedJwtException e) {
                throw new UnsupportedJwtException("JWT Token is unsupported");
            } catch (JwtException e) {
                throw new JwtException("JWT Token is invalid");
            } catch (IllegalArgumentException e) {
                throw new JwtException("Unable to get JWT Token");
            }
        }

        chain.doFilter(request, response);
    }

    private String getTokenFromRequestHeader(String header) {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
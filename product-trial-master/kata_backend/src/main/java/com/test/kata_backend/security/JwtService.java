package  com.test.kata_backend.security;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import  com.test.kata_backend.model.TokenCached;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private static final long TOKEN_EXPIRATION_TIME = 3600;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 86400 * 2;

    // Clé secrète utilisée pour signer le token
    private static final byte[] JWT_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();

    public String generateToken(String email) {
        return createToken(email , TOKEN_EXPIRATION_TIME);
    }

    private String createToken(String email, Long expirationTime) {
        Integer tokenId = generateNewTokenId();

        String token = Jwts.builder()
                .setSubject(email)
                .claim("tokenId", tokenId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getSigningKey())
                .compact();

        TokenCached.put(tokenId , token);

        return token;
    }

    public String generateRefreshToken(String email) {
        return createToken(email , REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return  extractClaim(token,Claims::getExpiration);
    }

    private<T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final  Claims claims = extractAllClaims(token);
        return  claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token) {
        Integer tokenId = getTokenId(token);
        return TokenCached.get(tokenId) != null && TokenCached.get(tokenId).equals(token) && !isTokenExpired(token);
    }

    private Integer generateNewTokenId() {
        Integer newId = null;
        while (newId == null || TokenCached.containsKey(newId)) {
            newId = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        }
        return newId;
    }

    public Integer getTokenId(String token) {
        return extractAllClaims(token).get("tokenId", Integer.class);
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY);
    }
}
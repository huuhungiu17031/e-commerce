package group6.ecommerce.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import group6.ecommerce.Repository.RefreshTokenRepository;
import group6.ecommerce.Repository.UserRepository;
import group6.ecommerce.exception.NotFoundException;
import group6.ecommerce.model.RefreshToken;
import group6.ecommerce.model.Users;
import group6.ecommerce.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final int duration = 24 * 3600 * 1000;
    @Value("${token.secrect.refreshKey}")
    private String JWT_REFRESH_TOKEN;

    private String generateRefreshToken(String email) {
        Map<String, Object> extraClaims = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        String randomString = Long.toString(System.currentTimeMillis()) + uuid.toString();
        extraClaims.put("randomString", randomString);
        return Jwts.builder()
                .setSubject(email)
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenValid(String token) {
        if (!extractExpiration(token).before(new Date())) {
            return true;
        }
        return false;
    }

    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Key getSigningKey() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_REFRESH_TOKEN));
        return key;
    }

    private String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    @Override
    public RefreshToken save(String token, Users user) {
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setRefreshToken(token);
        newRefreshToken.setUsers(user);
        return refreshTokenRepository.save(newRefreshToken);
    }

    @Override
    public RefreshToken processRefreshToken(String email) {
        Optional<Users> optional = userRepository.findByEmail(email);
        if (optional.isEmpty())
            throw new NotFoundException("Email does not exist");
        Users user = optional.get();
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(user.getId());
        System.out.println(optionalRefreshToken.get().getRefreshToken());
        if (optional.isEmpty())
            throw new NotFoundException("Refresh token does not exist");
        if (optionalRefreshToken.get().getRefreshToken() == null) {
            String refreshToken = generateRefreshToken(email);
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setRefreshToken(refreshToken);
            newRefreshToken.setUsers(user);
            return refreshTokenRepository.save(newRefreshToken);
        }
        RefreshToken availableRefreshToken = optionalRefreshToken.get();
        availableRefreshToken.setRefreshToken(generateRefreshToken(email));
        return refreshTokenRepository.save(availableRefreshToken);
    }

    @Override
    public RefreshToken handleGetNewRefreshToken(String token) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(token);
        if (optional.isEmpty())
            throw new NotFoundException("Refresh token does not exist");
        RefreshToken dbRefreshToken = optional.get();
        if (isTokenValid(dbRefreshToken.getRefreshToken())) {
            String email = extractEmail(token);
            String newToken = generateRefreshToken(email);
            dbRefreshToken.setRefreshToken(newToken);
            return refreshTokenRepository.save(dbRefreshToken);
        }
        refreshTokenRepository.deleteById(dbRefreshToken.getId());
        throw new NotFoundException("Sign in again");
    }

    @Override
    public void provokeToken(Integer userId) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByUserId(userId);
        if (optional.isEmpty())
            throw new NotFoundException("Refresh token does not exist");
        RefreshToken dbRefreshToken = optional.get();
        refreshTokenRepository.deleteById(dbRefreshToken.getId());
    }
}

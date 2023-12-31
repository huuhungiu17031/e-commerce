package group6.ecommerce.service;

import java.util.List;
import java.util.Map;

import group6.ecommerce.payload.response.JwtResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(String email, Map<String, Object> extraClaims);

    Claims extractAllClaims(String token);

    Boolean isTokenValid(String token, String email);

    String extractEmail(String token);

    JwtResponse getNewJwtToken(String token);

    JwtResponse generateJwtResponse(String email, List<String> roles);
}
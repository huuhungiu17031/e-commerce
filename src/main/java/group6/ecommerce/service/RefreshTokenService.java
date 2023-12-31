package group6.ecommerce.service;

import group6.ecommerce.model.RefreshToken;
import group6.ecommerce.model.Users;

public interface RefreshTokenService {

    RefreshToken save(String token, Users user);

    RefreshToken processRefreshToken(String email);

    RefreshToken handleGetNewRefreshToken(String token);

    void provokeToken(Integer token);
}
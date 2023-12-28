package group6.ecommerce.service;

import group6.ecommerce.payload.response.JwtResponse;

public interface UserService {
    JwtResponse login(String email, String password);
}

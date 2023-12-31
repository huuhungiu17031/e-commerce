package group6.ecommerce.service;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.LoginRequest;
import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.payload.response.JwtResponse;

public interface UserService {
    JwtResponse login(LoginRequest loginRequest);

    void register(UserRequest userRequest);

    Users findByEmail(String email);
}

package group6.ecommerce.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group6.ecommerce.payload.request.LoginRequest;
import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.payload.response.JwtResponse;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        userService.register(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Register successfully");
    }
}

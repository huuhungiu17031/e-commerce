package group6.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<String> signIn(@RequestBody UserRequest userRequest) {
        userService.login(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Login successfully");
    }
}

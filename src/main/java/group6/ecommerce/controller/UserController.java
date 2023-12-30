package group6.ecommerce.controller;

import group6.ecommerce.Repository.ProductRepository;
import group6.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group6.ecommerce.payload.request.UserRequest;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<String> signIn(@RequestBody UserRequest userRequest) {
        userService.login(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Login successfully");
    }
}

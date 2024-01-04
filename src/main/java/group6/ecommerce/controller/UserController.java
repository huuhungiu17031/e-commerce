package group6.ecommerce.controller;


import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.ChangePasswordRequest;
import group6.ecommerce.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final EmailService emailService;

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

    @PostMapping("forgetPassword/{email}")
    public ResponseEntity<String> forgetPassword(@PathVariable String email){
        Users users = userService.findByEmail(email);
        if(users != null){
            emailService.SendEmailTo(email, users.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Password was sent to your email");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong email");
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Boolean changePassword = userService.changePassword(changePasswordRequest);
        if(changePassword){
            return ResponseEntity.status(HttpStatus.OK).body("Change password successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("Wrong old password or the new password and confirm password do not match");
        }
    }
}

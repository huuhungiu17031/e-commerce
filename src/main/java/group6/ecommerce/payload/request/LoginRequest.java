package group6.ecommerce.payload.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}

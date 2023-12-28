package group6.ecommerce.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import group6.ecommerce.payload.response.JwtResponse;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse login(String email, String password) {
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authen);
        return null;
    }

}

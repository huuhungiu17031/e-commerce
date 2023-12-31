package group6.ecommerce.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import group6.ecommerce.model.Users;
import group6.ecommerce.service.UserService;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public CustomAuthenticationProvider(@Lazy PasswordEncoder passwordEncoder, @Lazy UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Users user = userService.findByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            List<GrantedAuthority> listRoles = new ArrayList<>();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
            listRoles.add(authority);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    listRoles);
            return token;
        }
        throw new RuntimeException("Wrong email or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean valid = authentication.equals(UsernamePasswordAuthenticationToken.class);
        return valid;
    }

}

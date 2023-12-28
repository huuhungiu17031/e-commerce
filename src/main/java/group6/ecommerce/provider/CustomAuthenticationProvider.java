package group6.ecommerce.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authenticate");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean valid = authentication.equals(UsernamePasswordAuthenticationToken.class);
        return valid;
    }

}

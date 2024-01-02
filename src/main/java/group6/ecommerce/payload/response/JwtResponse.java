package group6.ecommerce.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}

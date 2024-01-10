package group6.ecommerce.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class roleRespone {
    private String email;
    private String role;

    public roleRespone(String email, String role) {
        this.email = email;
        this.role = role;
    }
}

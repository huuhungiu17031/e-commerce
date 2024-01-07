package group6.ecommerce.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addCartRespone {
    private String message;
    public addCartRespone(String message) {
        this.message = message;
    }
}

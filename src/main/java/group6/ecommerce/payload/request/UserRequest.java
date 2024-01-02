package group6.ecommerce.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Integer id;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String city;
    private String district;
    private String ward;
}
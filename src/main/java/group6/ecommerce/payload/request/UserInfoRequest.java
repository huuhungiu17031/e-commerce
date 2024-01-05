package group6.ecommerce.payload.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequest {
    private String phone;
    private String address;
    //private String name;
    private String city;
    private String district;
    private String ward;

}

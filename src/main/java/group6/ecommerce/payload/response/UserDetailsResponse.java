package group6.ecommerce.payload.response;


import group6.ecommerce.model.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDetailsResponse {
    private int userId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String district;
    private String ward;

    public UserDetailsResponse(Users user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.district = user.getDistrict();
        this.ward = user.getWard();
    }

}

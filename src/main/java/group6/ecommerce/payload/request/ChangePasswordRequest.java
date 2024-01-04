package group6.ecommerce.payload.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String OldPassword;
    private String NewPassword;
    private String ConfirmPassword;
}

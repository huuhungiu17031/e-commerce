package group6.ecommerce.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class paymentVnpayRespone {
    public String status;
    public String messeage;
    public String url;

    public paymentVnpayRespone(String status, String messeage, String url) {
        this.status = status;
        this.messeage = messeage;
        this.url = url;
    }
}

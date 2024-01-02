package group6.ecommerce.payload.request;

import lombok.Data;


@Data
public class CouponRequest {
    private int id;
    private String code;
    private int discount;

}


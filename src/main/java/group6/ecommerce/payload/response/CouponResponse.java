package group6.ecommerce.payload.response;

import group6.ecommerce.model.Coupon;
import lombok.Data;

@Data
public class CouponResponse {
    private int id;
    private String code;
    private int discount;

    public CouponResponse(Coupon coupon){
        this.id = coupon.getId();
        this.code = coupon.getCode();
        this.discount = coupon.getDiscount();
    }
}


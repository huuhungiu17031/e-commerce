package group6.ecommerce.service;

import group6.ecommerce.model.Coupon;
import group6.ecommerce.payload.response.CouponResponse;

import java.util.List;

public interface CouponService {
    public boolean addCoupon(Coupon coupon);
    public List<Coupon> findAllCoupon();
    public boolean deleteCoupon(int id);
    public Coupon findCouponWithCode(String code);
}

package group6.ecommerce.service;

import group6.ecommerce.model.Coupon;
import java.util.List;

public interface CouponService {
    public boolean addCoupon(Coupon coupon);
    public List<Coupon> findAllCoupon();
    public boolean deleteCoupon(int id);

    public Coupon findCouponWithCode(String code);
    public Coupon findByCode (String code);

}

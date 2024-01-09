package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.CouponRepository;
import group6.ecommerce.model.Coupon;
import group6.ecommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpls implements CouponService {
    private final CouponRepository couponRepository;

    @Override
    public boolean addCoupon(Coupon coupon) {
        // Kiểm tra xem mã giảm giá đã tồn tại hay chưa
        boolean isCouponExists = couponRepository.findAll()
                .stream()
                .anyMatch(existingCoupon -> existingCoupon.getCode().equals(coupon.getCode()));
        if (!isCouponExists) {
            couponRepository.save(coupon);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCoupon(int id) {
        boolean isCouponExists = couponRepository.existsById(id);
        if (isCouponExists) {
            couponRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Coupon> findAllCoupon() {
        return couponRepository.findAll();
    }

    @Override

    public Coupon findCouponWithCode(String code) {
        Optional<Coupon> existingCoupon = couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCode().equals(code))
                .findFirst();

        return existingCoupon.orElseGet(() -> {
            Coupon newCoupon = new Coupon();
            newCoupon.setDiscount(0);
            return newCoupon;
        });
    }
    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code);
    }
}

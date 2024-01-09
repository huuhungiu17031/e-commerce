package group6.ecommerce.controller;

import group6.ecommerce.model.Coupon;
import group6.ecommerce.payload.request.CouponRequest;
import group6.ecommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/add")
    public ResponseEntity<String> addCoupon(@RequestBody CouponRequest couponRequest){
        Coupon coupon = new Coupon();
        coupon.setCode(couponRequest.getCode());
        coupon.setDiscount(couponRequest.getDiscount());
        if(couponService.addCoupon(coupon)){
            return ResponseEntity.status(HttpStatus.OK).body("Thêm mã giản giá thành công");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thêm mã giảm giá không thành công");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Coupon>> getAllCoupon(){
        List<Coupon> list = couponService.findAllCoupon();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int id) {

        if (couponService.deleteCoupon(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Xóa mã giảm giá thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không tồn tại");
        }
    }
    @GetMapping("/{code}")
    public ResponseEntity<Coupon> findCouponWithCode(@PathVariable String code){
        return new ResponseEntity<>(couponService.findCouponWithCode(code), HttpStatus.OK);
    }

}

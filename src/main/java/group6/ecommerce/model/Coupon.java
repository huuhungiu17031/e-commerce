package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Coupon")
@Data
@Getter
@Setter
public class Coupon {
    @Id
    @Column (name = "CouponId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "Code")
    private String code;
    @Column (name = "discount")
    private int discount;
}

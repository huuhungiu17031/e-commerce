package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Map;

@Entity
@Table (name = "Orders")
@Data
@Getter
@Setter
public class Order {
    @Id
    @Column (name = "OrderId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int Id;
    @Column (name = "OrderDate")
    Date orderDate;
    @Column (name = "Coupon")
    private String coupon;
    @Column (name = "Payment")
    private String payment;
    @Column (name = "Status")
    private String Status;
    @ManyToOne
    @JoinColumn (name = "UserId")
    private Users userOrder;
    @OneToMany (mappedBy = "ItemOrder")
    Map<String,Order_Details> listItems;
}

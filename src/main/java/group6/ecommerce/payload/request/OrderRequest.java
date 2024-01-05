package group6.ecommerce.payload.request;

import group6.ecommerce.model.Cart;
import group6.ecommerce.model.Order;
import group6.ecommerce.model.Order_Details;
import group6.ecommerce.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class OrderRequest {
    Date orderDate = new Date(System.currentTimeMillis());
    private String coupon = "";
    private String payment;
    private Users userOrder;


    public Order getOrder (){
        Order order = new Order();
        order.setOrderDate(this.orderDate);
        order.setUserOrder(this.userOrder);
        order.setCoupon(this.coupon);
        order.setPayment(this.payment);
        order.setStatus("pending");
        return  order;
    }
}

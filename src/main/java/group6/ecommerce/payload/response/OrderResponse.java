package group6.ecommerce.payload.response;

import group6.ecommerce.model.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class OrderResponse {
    private int id;
    private Date orderDate;
    private String coupon;
    private String payment;
    private String status;
//    private int userId;
    private UserDetailsResponse userOrder;
    private List<OrderDetailsResponse> orderDetails;

    public OrderResponse(Order o) {
        this.id = o.getId();
        this.coupon = o.getCoupon();
        this.payment = o.getPayment();
        this.status = o.getStatus();
        this.orderDate = o.getOrderDate();
        this.userOrder = new UserDetailsResponse(o.getUserOrder());
        List<OrderDetailsResponse> listOrderDetails = new ArrayList<>();
        o.getListItems().values().stream().forEach(e -> listOrderDetails.add(new OrderDetailsResponse(e)));
        this.orderDetails = listOrderDetails;
    }
}

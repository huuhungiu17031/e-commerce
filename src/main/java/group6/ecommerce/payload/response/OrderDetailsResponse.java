package group6.ecommerce.payload.response;


import group6.ecommerce.model.Order_Details;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailsResponse {
    private int orderDetailsId;
    private ProductRespone item;
    private String size;
    private String color;
    private int quantity;
    private int productId;


    public OrderDetailsResponse(Order_Details orderDetails){
        this.orderDetailsId = orderDetails.getId();
        this.size = orderDetails.getSize();
        this.color = orderDetails.getColor();
        this.quantity = orderDetails.getAmount();
        this.productId = orderDetails.getProduct().getId();
        this.item = new ProductRespone(orderDetails.getProduct());
    }
}

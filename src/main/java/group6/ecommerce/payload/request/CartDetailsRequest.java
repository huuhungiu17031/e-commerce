package group6.ecommerce.payload.request;

import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.Users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDetailsRequest {
    private int productId;
    private int amount;

    private String size;

    private String Color;

    private Product product;

    private Users user;
    public Cart_Details getCartDetails (){
        return new Cart_Details(this.amount,this.size,this.Color,user.getCart(),product);
    }
}

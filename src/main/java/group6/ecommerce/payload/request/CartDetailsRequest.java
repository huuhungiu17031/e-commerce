package group6.ecommerce.payload.request;

import group6.ecommerce.model.Cart;
import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.Product;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.service.UserService;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class CartDetailsRequest {

    private final ProductService productService;
    private final UserService userService;

    private int amount;

    private String size;

    private String Color;

    private Cart ItemCart;

    private int productId;

    private int userId;
    public Cart_Details getCartDetails (){
        return new Cart_Details(this.amount,this.size,this.Color,userService.findById(this.userId).getCart(),productService.findById(this.productId));
    }
}

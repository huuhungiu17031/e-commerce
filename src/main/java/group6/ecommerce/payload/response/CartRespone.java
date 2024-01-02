package group6.ecommerce.payload.response;


import group6.ecommerce.model.Cart;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class CartRespone {
    private int cartId;


    Map<String, CartDetailsRespone> listItems = new HashMap<>();

    public CartRespone(Cart cart) {
        this.cartId = cart.getCartId();
        for (String key : cart.getListItems().keySet()){
            listItems.put(key, new CartDetailsRespone(cart.getListItems().get(key)));
        }
    }
}

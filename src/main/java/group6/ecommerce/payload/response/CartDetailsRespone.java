package group6.ecommerce.payload.response;

import group6.ecommerce.model.Cart_Details;
import lombok.Getter;

@Getter
public class CartDetailsRespone {

    private int id;
    private int amount;
    private String size;
    private String Color;
    private ProductRespone product;
    private String key;

    public CartDetailsRespone(Cart_Details item) {
        this.id = item.getId();
        this.amount = item.getAmount();
        this.size = item.getSize();
        this.Color = item.getColor();
        this.product = new ProductRespone(item.getProduct());
        this.key = item.getProduct().getId()+item.getSize()+item.getColor();
    }
}

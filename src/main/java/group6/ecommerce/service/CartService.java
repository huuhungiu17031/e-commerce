package group6.ecommerce.service;

import group6.ecommerce.model.Cart;
import group6.ecommerce.model.Cart_Details;

public interface CartService {
    public String addTocart (Cart_Details item, int userId);
    public String removeToCart (Cart_Details item, int userId);
    public void save (Cart cart);
}

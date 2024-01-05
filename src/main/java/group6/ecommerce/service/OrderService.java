package group6.ecommerce.service;

import group6.ecommerce.model.Order;
import group6.ecommerce.payload.response.CheckOutRespone;

public interface OrderService {
    public CheckOutRespone CheckOut (Order order);
    Order findById (int id);


    public void cancel (Order order);

    public Order save (Order order);
}

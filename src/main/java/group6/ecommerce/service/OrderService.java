package group6.ecommerce.service;

import java.util.List;

import group6.ecommerce.model.Order;
import group6.ecommerce.payload.response.PaginationResponse;
import group6.ecommerce.payload.response.CheckOutRespone;
import group6.ecommerce.payload.response.OrderResponse;

public interface OrderService {

    String updateStatus(int orderId, String status);

    PaginationResponse listOrder(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, String status);

    public CheckOutRespone CheckOut (Order order);
    Order findById (int id);


    public void cancel (Order order);

    public Order save (Order order);

    List<OrderResponse> listOrder();
}

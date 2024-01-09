package group6.ecommerce.service;

import group6.ecommerce.model.Order;
import group6.ecommerce.payload.response.OrderResponse;
import group6.ecommerce.payload.response.PaginationResponse;
import group6.ecommerce.payload.response.CheckOutRespone;

import java.util.List;

public interface OrderService {

    String updateStatus(int orderId, String status);

    PaginationResponse listOrder(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, String status);

    public CheckOutRespone CheckOut (Order order);
    Order findById (int id);


    public void cancel (Order order);

    public Order save (Order order);
    List<OrderResponse> findOrderByUserId(int userId);
}

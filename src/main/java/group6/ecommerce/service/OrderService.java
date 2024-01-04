package group6.ecommerce.service;

import group6.ecommerce.model.Order;
import group6.ecommerce.payload.response.PaginationResponse;

public interface OrderService {
    public String CheckOut (Order order);

    String updateStatus(int orderId, String status);

    PaginationResponse listOrder(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, String status);
}

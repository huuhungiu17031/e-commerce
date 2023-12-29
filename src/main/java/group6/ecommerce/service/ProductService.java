package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Product findById (int id);

    Page<Product> findByPage (Pageable pages);

    Page<Product> findAllQuantityLarger0 (Pageable pageAble);
}

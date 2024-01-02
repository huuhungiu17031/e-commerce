package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Product findById(int id);

    Page<Product> findByPage(Pageable pages);


    int addNewProduct(Product product);


    PaginationResponse listProduct(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, Integer categoryId);

    List<Product> findProductsByName(String name);
}

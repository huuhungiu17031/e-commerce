package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {
    public Product findById(int id);

    Page<Product> findByPage(Pageable pages);

    Page<Product> findAllQuantityLarger0(Pageable pageAble);

    int addNewProduct(Product product);


    Page<Product> findByCategoryNameQuantityLarger0 (String categoryName,Pageable pageAble);

    List<Integer> getTop10RepurchaseProduct(@Param("year") int year, @Param("month") int month);

    Page<Product> findByCategoryNameQuantityLarger0(String categoryName, Pageable pageAble);

    PaginationResponse listProduct(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, Integer categoryId);

}

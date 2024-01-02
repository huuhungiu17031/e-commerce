package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Product findById(int id);

    Page<Product> findByPage(Pageable pages);


    int addNewProduct(Product product);


    PaginationResponse listProduct(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, Integer categoryId);
    public PaginationResponse listProductByName(Integer pageSize,Integer pageNum,String fields,String orderBy,Boolean getAll,String search);
}

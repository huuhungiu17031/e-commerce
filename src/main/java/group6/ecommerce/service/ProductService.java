package group6.ecommerce.service;

import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
=======
import org.springframework.data.repository.query.Param;

import java.util.List;
>>>>>>> 8dbf8dea13f168e5892b23ef9bf7fb486404a6a5

public interface ProductService {
    public Product findById(int id);

    Page<Product> findByPage(Pageable pages);
    Product addNewProduct(Product product);


    List<Integer> getTopRepurchaseProduct(@Param("year") int year, @Param("month") int month);


    PaginationResponse listProduct(Integer pageSize, Integer pageNum, String fields, String orderBy, Boolean getAll, Integer categoryId);

    public PaginationResponse listProductByName(Integer pageSize,Integer pageNum,String fields,String orderBy,Boolean getAll,String search);
<<<<<<< HEAD
    void deleteProduct (Integer id);
=======

>>>>>>> 8dbf8dea13f168e5892b23ef9bf7fb486404a6a5
}

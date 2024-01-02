package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductRepository;
import group6.ecommerce.model.Product;
import group6.ecommerce.payload.response.PaginationResponse;
import group6.ecommerce.payload.response.ProductRespone;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.utils.HandleSort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class ProductServiceImpls implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> findByPage(Pageable pages) {
        return productRepository.findAll(pages);
    }


    @Override
    public int addNewProduct(Product product) {
        return productRepository.saveAndFlush(product).getId();
    }


    @Override
    public PaginationResponse listProduct(
            Integer pageSize,
            Integer pageNum,
            String fields,
            String orderBy,
            Boolean getAll,
            Integer categoryId) {
        Sort sort = HandleSort.buildSortProperties(fields, orderBy);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> pageProduct = productRepository.findByCategoryAndSort(categoryId, pageable);
        return new PaginationResponse(
                pageNum,
                pageSize,
                pageProduct.getTotalElements(),
                pageProduct.isLast(),
                pageProduct.getTotalPages(),
                pageProduct.getContent().stream().map(product -> new ProductRespone(product)).toList());
    }

    public PaginationResponse listProductByName(
            Integer pageSize,
            Integer pageNum,
            String fields,
            String orderBy,
            Boolean getAll,
            String name) {
        Sort sort = HandleSort.buildSortProperties(fields, orderBy);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> pageProduct = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return new PaginationResponse(
                pageNum,
                pageSize,
                pageProduct.getTotalElements(),
                pageProduct.isLast(),
                pageProduct.getTotalPages(),
                pageProduct.getContent().stream().map(product -> new ProductRespone(product)).toList());
    }
}

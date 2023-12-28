package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductRepository;
import group6.ecommerce.model.Product;
import group6.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpls implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }
}

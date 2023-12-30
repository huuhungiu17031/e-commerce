package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductDetailsRepository;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpls implements ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
    @Override
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename(int productId, String colorName, String sizeName) {
        return productDetailsRepository.findProductDetailsByProductIdAndColornameAndSizename(productId,colorName,sizeName);
    }

    @Override
    public ProductDetails addNewProductDetail(ProductDetails productDetails) {
        return productDetailsRepository.saveAndFlush(productDetails);
    }
}

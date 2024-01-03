package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductDetailsRepository;
import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpls implements ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
    @Override
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename(int productId, String colorId, String sizeId) {
        return productDetailsRepository.findProductDetailsByProductIdAndColornameAndSizename(productId,colorId,sizeId);
    }

    @Override
    public void save(ProductDetails details) {
        productDetailsRepository.save(details);
    }
}

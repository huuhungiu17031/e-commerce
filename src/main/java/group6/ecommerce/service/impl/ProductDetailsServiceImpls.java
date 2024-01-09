package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ProductDetailsRepository;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductDetailsServiceImpls implements ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
    @Override
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename(int productId, String colorId, String sizeId) {
        return productDetailsRepository.findProductDetailsByProductIdAndColornameAndSizename(productId,colorId,sizeId);
    }

    @Override

    public ProductDetails addNewProductDetail(ProductDetails productDetails) {
        return productDetailsRepository.saveAndFlush(productDetails);
    }

    @Override
    public ProductDetails findById(Integer id) {
        return productDetailsRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        productDetailsRepository.deleteById(id);
    }
    public void save(ProductDetails details) {
        productDetailsRepository.save(details);
    }
}

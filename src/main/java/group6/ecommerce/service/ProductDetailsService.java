package group6.ecommerce.service;

import group6.ecommerce.model.ProductDetails;

public interface ProductDetailsService {
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename (int productId, String colorName, String sizeName);

    ProductDetails addNewProductDetail (ProductDetails productDetails);
}

package group6.ecommerce.service;

import group6.ecommerce.model.Cart_Details;
import group6.ecommerce.model.ProductDetails;

public interface ProductDetailsService {
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename (int productId, String colorName, String sizeName);
<<<<<<< HEAD

    ProductDetails addNewProductDetail (ProductDetails productDetails);
    ProductDetails findById(Integer id);
    void deleteById(Integer id);
=======
    public void save (ProductDetails details);
>>>>>>> 8dbf8dea13f168e5892b23ef9bf7fb486404a6a5
}

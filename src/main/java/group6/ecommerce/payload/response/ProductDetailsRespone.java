package group6.ecommerce.payload.response;


import group6.ecommerce.model.ProductDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDetailsRespone {
    private int productDetailsId;
    private String size;
    private String color;
    private int quantity;
    private boolean outOfStock;

    public ProductDetailsRespone(ProductDetails productDetails){
        this.productDetailsId = productDetails.getProductDetailsId();
        this.size = productDetails.getSize().getSizeName();
        this.color = productDetails.getColor().getName();
        this.quantity = productDetails.getQuantity();
        this.outOfStock = productDetails.isOutOfStock();
    }
}

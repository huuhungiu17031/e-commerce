package group6.ecommerce.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Data
public class ProductDetailRequest {
    private Integer productDetailsId;
    private String size;
    private String color;
    private int quantity;
    private boolean outOfStock;
}

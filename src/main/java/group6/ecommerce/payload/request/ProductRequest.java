package group6.ecommerce.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Data
public class ProductRequest {
    private String name;
    private int price;
    private int dimension;
    private int weight;
    private String material;
    private String category;
    private String imageUrls;
    private String type;
    private String description;
    private List<ProductDetailRequest> productDetails;
}

package group6.ecommerce.payload.request;

import group6.ecommerce.model.Category;
import group6.ecommerce.model.Type;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Data
public class ProductRequest {
    private String name;
    private int price;
    private int dimension;
    private int weight;
    private String material;
    private String categoryName;
    private String imageUrls;
    private String typeName;
    private String description;
    private String sizeName;
    private String colorName;
    private int quantity;
    private boolean outOfStock;
}

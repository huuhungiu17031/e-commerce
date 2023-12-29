package group6.ecommerce.payload.response;

import group6.ecommerce.model.Category;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.model.Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductRespone {
    private int id;
    private String name;
    private int price;
    private int dimension;
    private int weight;
    private String material;
    private String category;
    private String imageUrls;
    private String type;
    private String description;

    public ProductRespone(Product p) {
        this.id = p.getId();
        this.name =p.getName();
        this.price = p.getPrice();
        this.dimension = p.getDimension();
        this.weight = p.getWeight();
        this.category = p.getCategory().getCategoryName();
        this.imageUrls = p.getImageUrls();
        this.type = p.getType().getTypeName();
        this.description = p.getDescription();
    }
}

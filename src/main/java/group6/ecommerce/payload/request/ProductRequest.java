package group6.ecommerce.payload.request;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
import group6.ecommerce.model.Category;
import group6.ecommerce.model.ProductDetails;
import group6.ecommerce.model.Type;
=======

>>>>>>> 7d5c6772804c715dbf720d08247f581a2d651506
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
    private String categoryName;
    private String imageUrls;
    private String typeName;
    private String description;
    private List<ProductDetailRequest> productDetailRequestList;
}

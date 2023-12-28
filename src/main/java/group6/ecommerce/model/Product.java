package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "Product")
@Data
@Getter
@Setter
public class Product {
    @Id
    @Column (name = "ProductId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "ProductName")
    private String name;
    @Column (name = "ProductPrice")
    int price;
    @Column (name = "Dimension")
    private int dimension;
    @Column (name = "Weight")
    private int weight;
    @Column (name = "Material")
    private String material;
    @ManyToOne
    @JoinColumn (name = "CategoryId")
    private Category category;
    @Column (name = "ImageUrls", length = 500)
    private String imageUrls;
    @ManyToOne
    @JoinColumn (name = "TypeId")
    private Type type;
    @Column (name = "Description")
    private String description;
    @OneToMany(mappedBy = "Products")
    private List<ProductDetails> listProductDetails;
}

package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "ProductDetails")
@Data
@Getter
@Setter
public class ProductDetails {
    @Id
    @Column (name = "ProductDetailsId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int productDetailsId;
    @ManyToOne
    @JoinColumn (name = "SizeId")
    private Size size;
    @ManyToOne
    @JoinColumn (name = "ColorId")
    private Color color;
    @Column (name = "Quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn (name = "productId")
    Product Products;
    @Column (name = "OutOfStock")
    private boolean outOfStock;
}

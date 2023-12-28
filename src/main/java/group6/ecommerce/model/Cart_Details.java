package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Cart_Details")
@Data
@Getter
@Setter
public class Cart_Details {
    @Id
    @Column (name = "CartDetailsId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "Quantity")
    private int amount;
    @Column (name = "Size")
    private String size;
    @Column (name = "Color")
    private String Color;
    @ManyToOne
    @JoinColumn (name = "CartId")
    private Cart ItemCart;
    @ManyToOne
    @JoinColumn (name = "ProductId")
    Product product;
}

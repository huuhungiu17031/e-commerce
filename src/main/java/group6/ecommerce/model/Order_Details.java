package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "OrderDetails")
@Data
@Getter
@Setter
public class Order_Details {
    @Id
    @Column(name = "OrderDetailsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "Quantity")
    private int amount;
    @Column (name = "Size")
    private String size;
    @Column (name = "Color")
    private String Color;
    @ManyToOne
    @JoinColumn (name = "OrderId", referencedColumnName = "OrderId")
    private Order ItemOrder;
    @ManyToOne
    @JoinColumn (name = "ProductId")
    Product product;
}

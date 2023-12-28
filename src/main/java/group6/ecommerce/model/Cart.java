package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Table (name = "Cart")
@Data
@Getter
@Setter
public class Cart {
    @Id
    @Column (name = "CartId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int cartId;
    @OneToOne
    @JoinColumn (name = "UserId")
    private Users UserCart;
    @OneToMany (mappedBy = "ItemCart")
    Map<String,Cart_Details> listItems;
}

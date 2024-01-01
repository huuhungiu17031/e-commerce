package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Colors")
@Data
@Getter
@Setter
public class Color {
    @Id
    @Column (name = "ColorId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "ColorName")
    private String colorName;
}

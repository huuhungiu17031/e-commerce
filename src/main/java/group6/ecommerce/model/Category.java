package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Category")
@Data
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "CategoryId")
    private int id;
    @Column (name = "CategoryName")
    private String categoryName;
}

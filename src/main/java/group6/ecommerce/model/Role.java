package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Role")
@Data
@Getter
@Setter
public class Role {
    @Id
    @Column(name = "RoleId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int Id;
    @Column (name = "RoleName")
    private String roleName;
}

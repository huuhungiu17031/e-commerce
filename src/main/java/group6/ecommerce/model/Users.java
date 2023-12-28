package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "UserId")
    private int id;
    @Column (name = "Email", nullable = false,unique = true, length = 50)
    private String email;
    @Column (name = "Phone", nullable = false, unique = true, length = 50)
    private String phone;
    @Column (name = "Password", nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn (name = "RoleId")
    private Role role;
    @Column (name = "Address", nullable = false, length = 500)
    private String address;
    @Column (name = "City", nullable = false, length = 50)
    private String city;
    @Column (name = "District", nullable = false, length = 50)
    private String district;
    @Column (name = "Ward", nullable = false, length = 50)
    private String ward;
    @OneToOne (mappedBy = "UserCart")
    private Cart cart;
}

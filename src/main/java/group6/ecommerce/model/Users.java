package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer id;
    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "Phone", nullable = false, unique = true, length = 50)
    private String phone;
    @Column(name = "Password", nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;
    @Column(name = "Address", nullable = false, length = 500)
    private String address;
    @Column(name = "City", nullable = false, length = 50)
    private String city;
    @Column(name = "District", nullable = false, length = 50)
    private String district;
    @Column(name = "Ward", nullable = false, length = 50)
    private String ward;
    @OneToOne(mappedBy = "UserCart", fetch = FetchType.EAGER)
    private Cart cart;
    @OneToMany (mappedBy = "userOrder", fetch = FetchType.EAGER)
    List<Order> listOrder;
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }
}

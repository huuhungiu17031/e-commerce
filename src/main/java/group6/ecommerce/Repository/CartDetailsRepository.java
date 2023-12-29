package group6.ecommerce.Repository;

import group6.ecommerce.model.Cart_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepository extends JpaRepository<Cart_Details,Integer> {
}

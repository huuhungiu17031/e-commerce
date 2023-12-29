package group6.ecommerce.Repository;

import group6.ecommerce.model.Order_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<Order_Details,Integer> {
}

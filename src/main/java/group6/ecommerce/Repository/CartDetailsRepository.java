package group6.ecommerce.Repository;

import group6.ecommerce.model.Cart_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepository extends JpaRepository<Cart_Details,Integer> {

    @Modifying
    @Query (value = "delete cart_details where list_items_key = ?1 and cart_id = ?2", nativeQuery = true)
    public void deleteByKey (String key, int cartId);
}

package group6.ecommerce.Repository;

import group6.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT o FROM Order o WHERE (:status IS NULL OR o.Status = :status)")
    Page<Order> findByStatusAndSort(@Param("status") String status, Pageable pageable);
    @Query(value = "select * from Orders where user_id=?1",nativeQuery = true)
    List<Order> findOrderByUserId(int userId);
}

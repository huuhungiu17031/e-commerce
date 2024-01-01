package group6.ecommerce.Repository;

import group6.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query (value = "select * from product \n" +
            "where product_id in (select distinct product_details.product_id from product_details\n" +
            "group by product_details.product_id, product_details.quantity\n" +
            "having product_details.quantity > 0)", nativeQuery = true)
    Page<Product> findAllQuantityLarger0 (Pageable pageAble);

    public Product saveAndFlush(Product product);

    @Query (value = "select * from product \n" +
            "where product_id in (select distinct product_id \n" +
            "from product join category on product.category_id = category.category_id \n" +
            "where category_name = ?1) and product_id in ((select distinct product_details.product_id \n" +
            "from product_details \n" +
            "group by product_details.product_id, product_details.quantity\n" +
            "having product_details.quantity > 0))\n", nativeQuery = true)
    Page<Product> findByCategoryNameQuantityLarger0 (String categoryName,Pageable pageAble);

    @Query( value = "SELECT TOP 10 p.product_id " +
            "FROM product p " +
            "INNER JOIN order_details od ON p.product_id = od.product_id " +
            "INNER JOIN order o ON od.order_id = o.order_id " +
            "WHERE YEAR(o.order_date) = :year AND MONTH(o.order_date) = :month " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY COUNT(o.order_id) - COUNT(DISTINCT o.user_id) DESC ", nativeQuery = true)
    List<Integer> getTop10RepurchaseProduct(@Param("year") int year,@Param("month") int month);
}

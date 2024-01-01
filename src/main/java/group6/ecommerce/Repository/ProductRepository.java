package group6.ecommerce.Repository;

import group6.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    public Product saveAndFlush(Product product);

    @Query (value = "select * from product \n" +
            "where product_id in (select distinct product_id \n" +
            "from product \n" +
            "where (?1 IS NULL or product.category_id = ?1)) and product_id in ((select distinct product_details.product_id \n" +
            "from product_details \n" +
            "group by product_details.product_id, product_details.quantity\n" +
            "having product_details.quantity > 0))\n", nativeQuery = true)
    Page<Product> findByCategoryAndSort(Integer categoryId, Pageable pageable);
}

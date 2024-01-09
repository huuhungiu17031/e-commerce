package group6.ecommerce.Repository;

import group6.ecommerce.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer> {
    @Query (value = "select product_details.* from product_details join size on product_details.size_id = size.size_id\n" +
            "join colors on colors.color_id = product_details.color_id\n" +
            "where product_details.product_id = ?1 and colors.color_name = ?2 and size.size_name = ?3",nativeQuery = true)
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename (int productId, String colorName, String sizeName);

    // ProductDetails saveAndFlush(ProductDetails productDetails);
    void deleteById(Integer id);
    @Modifying
    @Query(value = "delete from product_details where product_id = ?1", nativeQuery = true)
    void deleteByProductId(Integer productId);
}

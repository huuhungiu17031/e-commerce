package group6.ecommerce.Repository;

import group6.ecommerce.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer> {
    @Query (value = "select p.product_id,p.color_id,p.out_of_stock,p.product_details_id,p.quantity,p.size_id,p.quantity\n" +
            "from product_details p join colors on p.color_id = colors.color_id\n" +
            "join size on p.product_id = size.size_id\n" +
            "Where product_id ?1 and colors.color_name = ?2 and size.size_name = ?3", nativeQuery = true)
    public ProductDetails findProductDetailsByProductIdAndColornameAndSizename (int productId, String colorName, String sizeName);
}

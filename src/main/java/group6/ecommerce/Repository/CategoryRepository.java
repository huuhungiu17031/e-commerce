package group6.ecommerce.Repository;

import group6.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query(value = "select * from category where category_name=?1" , nativeQuery = true)
    Category findCategoryByName(String name);
}

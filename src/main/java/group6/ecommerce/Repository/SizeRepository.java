package group6.ecommerce.Repository;

import group6.ecommerce.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query(value = "select * from size where size_name=?1" , nativeQuery = true)
    Size findSizeByName(String name);
}

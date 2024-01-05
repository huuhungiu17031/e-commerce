package group6.ecommerce.Repository;

import group6.ecommerce.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "select * from colors where color_name=?1" , nativeQuery = true)
    Color findColorByName(String name);
}

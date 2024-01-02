package group6.ecommerce.Repository;

import group6.ecommerce.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    @Query(value = "select * from type where type_name=?1", nativeQuery = true)
    Type findTypeByName(String name);
}

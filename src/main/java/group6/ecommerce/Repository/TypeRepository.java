package group6.ecommerce.Repository;

import group6.ecommerce.model.Type;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.function.Function;
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    @Query(value = "select * from type where type_name=?1", nativeQuery = true)
    Type findTypeByName(String name);
}

package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByNameAndCategoryIdAndActiveTrue(String name, Integer categoryId);
    List<Product> findAllByActiveTrue();
    Optional<Product> findByIdAndActiveTrue(Integer id);
}

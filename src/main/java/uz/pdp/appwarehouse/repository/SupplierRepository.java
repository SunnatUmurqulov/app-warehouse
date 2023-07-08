package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    List<Supplier> findAllByActiveTrue();

    Optional<Supplier> findByIdAndActiveTrue(Integer id);
}

package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Warehouse;

import java.util.Optional;

public interface WareHouseRepository extends JpaRepository<Warehouse, Integer> {
    boolean existsByNameAndActiveTrue(String name);
    Optional<Warehouse> findByIdAndActiveTrue(Integer warehouseId);
}

package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Input;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InputRepository extends JpaRepository<Input, Integer> {
    boolean existsByFactureNumberAndDate(String factureNumber, Timestamp date);

}

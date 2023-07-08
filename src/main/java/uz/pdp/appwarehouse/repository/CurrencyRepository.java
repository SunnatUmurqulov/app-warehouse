package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.enums.CurrencyType;

import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    boolean existsByCurrencyType(CurrencyType currencyType);

    Optional<Currency> findByIdAndActiveTrue(Integer currencyId);
}

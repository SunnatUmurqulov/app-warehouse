package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.enums.CurrencyType;
import uz.pdp.appwarehouse.payload.CurrencyDto;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public HttpEntity<?> addCurrency(CurrencyDto currencyDto) {
        boolean exists = currencyRepository.existsByCurrencyType(CurrencyType.valueOf(currencyDto.getCurrencyType()));
        if (exists) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Currency type found", false));
        }
        Currency currency = new Currency();
        currency.setCurrencyType(CurrencyType.valueOf(currencyDto.getCurrencyType()));
        currency.setName(currencyDto.getName());
        currencyRepository.save(currency);
        return ResponseEntity.ok(new ApiResponse("Currency type successfully added", true));
    }

    public HttpEntity<?> getAll() {
        List<Currency> currencyList = currencyRepository.findAll();
        return ResponseEntity.ok(currencyList);
    }

    public HttpEntity<?> getCurrency(Integer id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (currencyOptional.isPresent() && currencyOptional.get().isActive()) {
            return ResponseEntity.ok(currencyOptional);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No currency type found with this id", false));
    }

    public HttpEntity<?> update(Integer id, CurrencyDto currencyDto) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (currencyOptional.isPresent() && currencyOptional.get().isActive()) {
            Currency currency = currencyOptional.get();
            currency.setName(currencyDto.getName());
            currency.setCurrencyType(CurrencyType.valueOf(currencyDto.getCurrencyType()));
            currencyRepository.save(currency);
            return ResponseEntity.ok(new ApiResponse("Currency successfully updated",true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No currency type found with this id",false));
    }

    public HttpEntity<?> delete(Integer id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (currencyOptional.isPresent() && currencyOptional.get().isActive()){
            Currency currency = currencyOptional.get();
            currency.setActive(false);
            currencyRepository.save(currency);
            return ResponseEntity.ok(new ApiResponse("Currency successfully deleted",true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No currency type found with this id",false));
    }
}

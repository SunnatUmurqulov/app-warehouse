package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.payload.CurrencyDto;
import uz.pdp.appwarehouse.service.CurrencyService;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @PostMapping
    public HttpEntity<?> addCurrency(@RequestBody CurrencyDto currencyDto){
        return currencyService.addCurrency(currencyDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCurrency(@PathVariable Integer id){
        return currencyService.getCurrency(id);
    }

    @PutMapping("/change/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @RequestBody CurrencyDto currencyDto){
        return currencyService.update(id,currencyDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteCurrency(@PathVariable Integer id){
        return currencyService.delete(id);
    }
}

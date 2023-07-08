package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WareHouseRepository;

import java.util.List;
import java.util.Optional;

import static uz.pdp.appwarehouse.utill.GenerateNumber.generateUniqueNumber;


@Service
@RequiredArgsConstructor
public class InputService {
    private final InputRepository inputRepository;
    private final WareHouseRepository wareHouseRepository;
    private final SupplierRepository supplierRepository;
    private final CurrencyRepository currencyRepository;
    public HttpEntity<?> warehouseInput(InputDto inputDto) {
        boolean exists = inputRepository.existsByFactureNumberAndDate
                (inputDto.getFactureNumber(),inputDto.getDate());
        if (exists){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("This input is already registered",false));
        }
        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(inputDto.getWarehouseId())
                .orElseThrow(() -> new DataNotFoundException("Such id warehouse does not exist or is not active"));

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(inputDto.getSupplierId())
                .orElseThrow(() -> new DataNotFoundException("This is supplier does not exist or is not active"));

        Currency currency = currencyRepository.findByIdAndActiveTrue(inputDto.getCurrencyId())
                .orElseThrow(() -> new DataNotFoundException("Such currency does not exist or is not active"));


        Input input = Input.builder()
                .date(inputDto.getDate())
                .warehouse(warehouse)
                .supplier(supplier)
                .currency(currency)
                .factureNumber(inputDto.getFactureNumber())
                .code(generateUniqueNumber())
                .build();
        inputRepository.save(input);
        return ResponseEntity.ok(new ApiResponse("Successfully added",true));
    }

    public HttpEntity<?> getAllInput() {
        List<Input> inputList = inputRepository.findAll();
        return ResponseEntity.ok(inputList);
    }

    public HttpEntity<?> getInput(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            return ResponseEntity.ok(optionalInput.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No Input found with this id",false));
    }

    public HttpEntity<?> updateInput(Integer id, InputDto inputDto) {

        Warehouse warehouse = wareHouseRepository.findByIdAndActiveTrue(inputDto.getWarehouseId())
                .orElseThrow(() -> new DataNotFoundException("Such id warehouse does not exist or is not active"));

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(inputDto.getSupplierId())
                .orElseThrow(() -> new DataNotFoundException("This is supplier does not exist or is not active"));

        Currency currency = currencyRepository.findByIdAndActiveTrue(inputDto.getCurrencyId())
                .orElseThrow(() -> new DataNotFoundException("Such currency does not exist or is not active"));

        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            Input input = optionalInput.get();
            input.setFactureNumber(inputDto.getFactureNumber());
            input.setSupplier(supplier);
            input.setCurrency(currency);
            input.setWarehouse(warehouse);
            inputRepository.save(input);
            return ResponseEntity.ok(new ApiResponse("Input successfully updated",true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No Input found with this Id",false));
    }

    public HttpEntity<?> deleted(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            inputRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Input deleted",true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No Input found with this id",false));
    }

}

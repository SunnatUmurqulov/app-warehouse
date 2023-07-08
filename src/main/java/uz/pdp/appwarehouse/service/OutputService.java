package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.WareHouseRepository;
import uz.pdp.appwarehouse.utill.GenerateNumber;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static uz.pdp.appwarehouse.utill.GenerateNumber.generateUniqueNumber;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final OutputRepository outputRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ClientRepository clientRepository;
    private final CurrencyRepository currencyRepository;

    public HttpEntity<?> addOutput(OutputDto outputDto) {
        boolean exists = outputRepository.existsByFactureNumber(outputDto.getFactureNumber());
        if (exists) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Such an output exists", false));
        }

        Warehouse warehouse = wareHouseRepository.findById(outputDto.getWarehouseId())
                .orElseThrow(() -> new DataNotFoundException("Warehouse id not found"));

        Currency currency = currencyRepository.findById(outputDto.getCurrencyId())
                .orElseThrow(() -> new DataNotFoundException("No such currency exists"));

        Client client = clientRepository.findById(outputDto.getClientId())
                .orElseThrow(() -> new DataNotFoundException("Client id does not exist"));

        Output output = Output.builder()
                .factureNumber(outputDto.getFactureNumber())
                .warehouse(warehouse)
                .client(client)
                .currency(currency)
                .date(outputDto.getDate())
                .code(generateUniqueNumber())
                .build();
        outputRepository.save(output);
        return ResponseEntity.ok(new ApiResponse("Successfully created", true));
    }

    public HttpEntity<?> getAllOutput() {
        List<Output> outputList = outputRepository.findAll();
        return ResponseEntity.ok(outputList);
    }

    public HttpEntity<?> getOutput(Integer id) {
        Output output = outputRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));
        return ResponseEntity.ok(output);
    }

    public HttpEntity<?> updateOutput(Integer id, OutputDto outputDto) {
        Output output = outputRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));

        Currency currency = currencyRepository.findById(outputDto.getCurrencyId())
                .orElseThrow(() -> new DataNotFoundException("No such currency exists"));

        Client client = clientRepository.findById(outputDto.getClientId())
                .orElseThrow(() -> new DataNotFoundException("Client id does not exist"));

        Warehouse warehouse = wareHouseRepository.findById(outputDto.getWarehouseId())
                .orElseThrow(() -> new DataNotFoundException("Warehouse id not found"));

        output.setClient(client);
        output.setWarehouse(warehouse);
        output.setDate(outputDto.getDate());
        output.setCurrency(currency);
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);

        return ResponseEntity.ok(output);
    }

    public HttpEntity<?> deleted(Integer id) {
        Output output = outputRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));
        outputRepository.delete(output);
        return ResponseEntity.ok(new ApiResponse("Deleted successfully",true));
    }
}

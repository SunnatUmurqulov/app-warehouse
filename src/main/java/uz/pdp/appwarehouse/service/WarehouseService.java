package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.WareHouseDto;
import uz.pdp.appwarehouse.repository.WareHouseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WareHouseRepository wareHouseRepository;

    public HttpEntity<?> addWareHouse(WareHouseDto wareHouseDto) {
            boolean existsByName = wareHouseRepository.existsByNameAndActiveTrue(wareHouseDto.getName());
        if (existsByName) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Such a repository name exists", false));
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(wareHouseDto.getName());
        wareHouseRepository.save(warehouse);

        return ResponseEntity.ok(new ApiResponse("Successfully added", true));
    }

    public HttpEntity<?> getOneWareHouse(Integer id) {
        Optional<Warehouse> optionalWarehouse = wareHouseRepository.findById(id);
        if (optionalWarehouse.isPresent() && optionalWarehouse.get().isActive()) {
            return ResponseEntity.ok(optionalWarehouse.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No such repository name exists", false));
    }

    public HttpEntity<?> getAll() {
        //todo validation check
        List<Warehouse> warehouseList = wareHouseRepository.findAll();
        return ResponseEntity.ok(warehouseList);
    }

    public HttpEntity<?> update(Integer id, WareHouseDto wareHouseDto) {
        Optional<Warehouse> optionalWarehouse = wareHouseRepository.findById(id);
        if (optionalWarehouse.isPresent() && optionalWarehouse.get().isActive()) {
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setName(wareHouseDto.getName());
            wareHouseRepository.save(warehouse);
            return ResponseEntity.ok(new ApiResponse("Warehouse data edited successfully", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Warehouse not found", false));
    }

    public HttpEntity<?> deleteWareHouse(Integer id) {
        Optional<Warehouse> optionalWarehouse = wareHouseRepository.findById(id);
        if (optionalWarehouse.isPresent() && optionalWarehouse.get().isActive()){
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setActive(false);
            wareHouseRepository.save(warehouse);
            return ResponseEntity.ok(new ApiResponse("Warehouse updated successfully",true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Warehouse not found",false));
    }
}

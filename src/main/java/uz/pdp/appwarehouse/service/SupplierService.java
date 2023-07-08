package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public HttpEntity<?> addSupplier(SupplierDto supplierDto) {
        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplierDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("There is such a supplier", false));
        }
        Supplier supplier = new Supplier();
        supplier.setName(supplier.getName());
        supplier.setPhoneNumber(supplier.getPhoneNumber());
        supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Supplier added successfully", true));
    }

    public HttpEntity<?> getOneSupplier(Integer id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            return ResponseEntity.ok(supplierOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Supplier not found", false));
    }

    public HttpEntity<?> editSupplier(Integer id, SupplierDto supplierDto) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isPresent() && supplierOptional.get().isActive()) {
            Supplier supplier = new Supplier();
            supplier.setName(supplierDto.getName());
            supplier.setPhoneNumber(supplierDto.getPhoneNumber());
            supplierRepository.save(supplier);
            return ResponseEntity.ok(new ApiResponse("Edited successfully", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No such supplier found", false));
    }

    public HttpEntity<?> getAll() {
        List<Supplier> supplierList = supplierRepository.findAllAndActiveTrue();
        return ResponseEntity.ok(supplierList);
    }

    public HttpEntity<?> delete(Integer id) {
        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new DataNotFoundException("No such supplier found"));
        supplier.setActive(false);
        supplierRepository.save(supplier);
        return ResponseEntity.ok(new ApiResponse("Successfully deleted", true));
    }
}

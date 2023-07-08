package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.service.SupplierService;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/register")
    public HttpEntity<?> addSupplier(@RequestBody SupplierDto supplierDto){
        return supplierService.addSupplier(supplierDto);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneSupplier(@PathVariable Integer id){
        return supplierService.getOneSupplier(id);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return supplierService.getAll();
    }

    @PutMapping("/change/{id}")
    public HttpEntity<?> editSupplier(@PathVariable Integer id, @RequestBody SupplierDto supplierDto){
        return supplierService.editSupplier(id,supplierDto);
    }

    @DeleteMapping("/deleted/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return supplierService.delete(id);
    }

}

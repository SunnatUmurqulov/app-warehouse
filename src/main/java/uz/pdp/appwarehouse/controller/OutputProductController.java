package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.service.OutputProductService;

@RestController
@RequestMapping("/api/output-product")
@RequiredArgsConstructor
public class OutputProductController {
    private final OutputProductService outputProductService;

    @PostMapping
    public ApiResponse addOutputProduct(@RequestBody OutputProductDto outputProductDto){
        return outputProductService.addOutputProduct(outputProductDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return outputProductService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOutputProduct(@PathVariable Integer id){
        return outputProductService.getOutputProduct(id);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateOutputProduct(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto){
        return outputProductService.update(id,outputProductDto);
    }

    @DeleteMapping("/deleted/{id}")
    public ApiResponse deletedOutputProduct(@PathVariable Integer id){
        return outputProductService.deleted(id);
    }
}

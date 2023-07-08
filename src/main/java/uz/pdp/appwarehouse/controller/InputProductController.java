package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.service.InputProductService;

@RestController
@RequestMapping("/api/input-product")
@RequiredArgsConstructor
public class InputProductController {
    private final InputProductService inputProductService;

    @PostMapping
    public ApiResponse addInputProduct(@RequestBody InputProductDto inputProductDto){
        return inputProductService.addInputProduct(inputProductDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return inputProductService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getInputProduct(@PathVariable Integer id){
        return inputProductService.getInputProduct(id);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse update(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto){
        return inputProductService.update(id,inputProductDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deletedInputProduct(@PathVariable Integer id){
        return inputProductService.deleted(id);
    }
}

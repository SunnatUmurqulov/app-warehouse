package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.service.ProductService;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneProduct(@PathVariable Integer id){
        return productService.getOneProduct(id);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return productService.getAll();
    }

    @PutMapping("/change/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @RequestBody ProductDto productDto){
        return productService.update(id, productDto);
    }

    @DeleteMapping("/deleted/{id}")
    public HttpEntity<?> deleted(@PathVariable Integer id){
        return productService.deleted(id);
    }
}

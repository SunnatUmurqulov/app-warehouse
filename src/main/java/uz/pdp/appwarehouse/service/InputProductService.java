package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InputProductService {
    private final InputRepository inputRepository;
    private final ProductRepository productRepository;
    private final InputProductRepository inputProductRepository;
    public ApiResponse addInputProduct(InputProductDto inputProductDto) {
        Product product = productRepository.findByIdAndActiveTrue(inputProductDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("The product with this id does not exist or is not active"));

        Input input = inputRepository.findById(inputProductDto.getInputId())
                .orElseThrow(() -> new DataNotFoundException("There is no input with this id"));

        InputProduct inputProduct = InputProduct.builder()
                .product(product)
                .amount(inputProductDto.getAmount())
                .price(inputProductDto.getPrice())
                .input(input)
                .exipireDate(inputProductDto.getExipireDate())
                .build();
        inputProductRepository.save(inputProduct);
        return new ApiResponse("Input product successfully added",true);
    }

    public HttpEntity<?> getAll() {
        List<InputProduct> inputProductList = inputProductRepository.findAll();
        return ResponseEntity.ok(inputProductList);
    }

    public HttpEntity<?> getInputProduct(Integer id) {
        InputProduct inputProduct = inputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no input product with this id"));
        return ResponseEntity.ok(inputProduct);
    }

    public ApiResponse update(Integer id, InputProductDto inputProductDto) {
        InputProduct inputProduct = inputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no input product with this id"));

        Product product = productRepository.findByIdAndActiveTrue(inputProductDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("The product with this id does not exist or is not active"));

        Input input = inputRepository.findById(inputProductDto.getInputId())
                .orElseThrow(() -> new DataNotFoundException("There is no input with this id"));

        inputProduct.setProduct(product);
        inputProduct.setInput(input);
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setExipireDate(inputProductDto.getExipireDate());
        inputProductRepository.save(inputProduct);
        return new ApiResponse("Input product successfully edited",true);
    }

    public ApiResponse deleted(Integer id) {
        InputProduct inputProduct = inputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no input product with this id"));
        inputProductRepository.delete(inputProduct);
        return new ApiResponse("Input product successfully deleted",true);
    }
}

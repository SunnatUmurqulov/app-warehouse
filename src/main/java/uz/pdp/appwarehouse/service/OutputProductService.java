package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutputProductService {
    private final OutputProductRepository outputProductRepository;
    private final ProductRepository productRepository;
    private final OutputRepository outputRepository;

    public ApiResponse addOutputProduct(OutputProductDto outputProductDto) {
        Product product = productRepository.findById(outputProductDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product id not found"));

        Output output = outputRepository.findById(outputProductDto.getOutputId())
                .orElseThrow(() -> new DataNotFoundException("Output id not found"));

        OutputProduct outputProduct = OutputProduct.builder()
                .product(product)
                .output(output)
                .amount(outputProductDto.getAmount())
                .price(outputProductDto.getPrice())
                .build();
        outputProductRepository.save(outputProduct);
        return new ApiResponse("Created successfully", true);
    }


    public HttpEntity<?> getAll() {
        List<OutputProduct> productList = outputProductRepository.findAll();
        return ResponseEntity.ok(productList);
    }

    public HttpEntity<?> getOutputProduct(Integer id) {
        OutputProduct outputProduct = outputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));
        return ResponseEntity.ok(outputProduct);
    }


    public ApiResponse update(Integer id, OutputProductDto outputProductDto) {
        OutputProduct outputProduct = outputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));

        Product product = productRepository.findById(outputProductDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product id not found"));

        Output output = outputRepository.findById(outputProductDto.getOutputId())
                .orElseThrow(() -> new DataNotFoundException("Output id not found"));

        outputProduct.setProduct(product);
        outputProduct.setOutput(output);
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProductRepository.save(outputProduct);
        return new ApiResponse("Edited successfully",true);
    }


    public ApiResponse deleted(Integer id) {
        OutputProduct outputProduct = outputProductRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No such id found"));
        outputProductRepository.delete(outputProduct);
        return new ApiResponse("Deleted successfully",true);
    }
}

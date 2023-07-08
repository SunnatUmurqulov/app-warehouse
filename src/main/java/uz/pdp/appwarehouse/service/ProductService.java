package uz.pdp.appwarehouse.service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.exception.DataNotFoundException;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;

import static uz.pdp.appwarehouse.utill.GenerateNumber.generateUniqueNumber;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final MeasurementRepository measurementRepository;

    public HttpEntity<?> addProduct(ProductDto productDto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryIdAndActiveTrue(
                productDto.getName(), productDto.getCategoryId());

        if (existsByNameAndCategoryId) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Product by exists", false));
        }

        Category category = categoryRepository.findByIdAndActiveTrue(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("This category does not exist"));


        Attachment attachment = attachmentRepository.findById(productDto.getPhotoId())
                .orElseThrow(() -> new DataNotFoundException("No such photo exists"));

        Measurement measurement = measurementRepository.findByIdAndActiveTrue(productDto.getMeasurementId())
                .orElseThrow(() -> new DataNotFoundException("There is no such unit of measurement"));


        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(generateUniqueNumber());
        product.setCategory(category);
        product.setPhoto(attachment);
        product.setMeasurement(measurement);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Product added successfully", true));
    }

    public HttpEntity<?> getOneProduct(Integer id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return ResponseEntity.ok(product);
    }

    public HttpEntity<?> getAll() {
        List<Product> productList = productRepository.findAllAndActiveTrue();
        return ResponseEntity.ok(productList);
    }

    public HttpEntity<?> update(Integer id, ProductDto productDto) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setName(productDto.getName());
        productRepository.save(product);
        return ResponseEntity.ok(new ApiResponse("Product edited",true));
    }

    public HttpEntity<?> deleted(Integer id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
        return ResponseEntity.ok(new ApiResponse("Product deleted",true));
    }
}

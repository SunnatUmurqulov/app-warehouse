package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(apiResponse.isSuccess() ? 200 : 409);
    }

    @GetMapping
    public HttpEntity<?> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneCategory(@PathVariable Integer id) {
        return categoryService.getOneCategory(id);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.ok(apiResponse.isSuccess() ? 200 : 409);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deletedCategory(@PathVariable Integer id) {
        ApiResponse apiResponse = categoryService.deletedCategory(id);
        return ResponseEntity.ok(apiResponse.isSuccess() ? 200 : 409);
    }
}

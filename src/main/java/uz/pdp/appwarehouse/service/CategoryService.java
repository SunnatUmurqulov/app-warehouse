package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResponse addCategory(CategoryDto categoryDto) {
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName) {
            return new ApiResponse("Category by exists", false);
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (categoryOptional.isEmpty()) {
                return new ApiResponse("parent category does not exist", false);
            }
            category.setParentCategory(categoryOptional.get());
        }
        categoryRepository.save(category);
        return new ApiResponse("Successfully saved", true);
    }

    public HttpEntity<?> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }

    public HttpEntity<?> getOneCategory(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No such category exists", false));
    }

    public ApiResponse editCategory(Integer id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("No such category name found", false);
    }

    public ApiResponse deletedCategory(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setActive(false);
            categoryRepository.save(category);
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("No such id found", false);
    }
}

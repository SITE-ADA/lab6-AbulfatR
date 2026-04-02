package az.edu.ada.wm2.lab6.controller;

import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<CategoryResponseDto> addProductToCategory(
            @PathVariable UUID categoryId,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(categoryService.addProduct(categoryId, productId));
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.getProducts(categoryId));
    }
}
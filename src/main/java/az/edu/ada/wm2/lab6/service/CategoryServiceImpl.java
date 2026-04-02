package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.CategoryMapper;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository,
                               ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto) {
        // Manual mapping from RequestDto to Entity
        Category category = CategoryMapper.toEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        // Manual mapping from Entity to ResponseDto
        return CategoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResponseDto addProduct(UUID categoryId, UUID productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Make sure neither list is null
        if (category.getProducts() == null) {
            category.setProducts(new ArrayList<>());
        }
        if (product.getCategories() == null) {
            product.setCategories(new ArrayList<>());
        }

        category.getProducts().add(product);
        product.getCategories().add(category);

        productRepository.save(product);
        return CategoryMapper.toResponseDto(category);
    }

    @Override
    public List<ProductResponseDto> getProducts(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Use MapStruct ProductMapper to convert the product list to DTOs
        return category.getProducts().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
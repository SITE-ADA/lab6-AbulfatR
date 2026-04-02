package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface    ProductService {
    Product createProduct(Product product);
    Product getProductById(UUID id);
    List<Product> getAllProducts();
    Product updateProduct(UUID id, Product product);
    void deleteProduct(UUID id);
    List<Product> getProductsExpiringBefore(LocalDate date);
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}

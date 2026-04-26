package com.Ecommerce.App.service;

import com.Ecommerce.App.dto.ProductResponseDTO;
import com.Ecommerce.App.entity.Inventory;
import com.Ecommerce.App.entity.Product;
import com.Ecommerce.App.repository.CategoryRepository;
import com.Ecommerce.App.repository.InventoryRepository;
import com.Ecommerce.App.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository   productRepository;
    private final CategoryRepository  categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final CloudinaryService   cloudinaryService;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getProductsByCategory(
            Long categoryId) {
        return productRepository
                .findByIsActiveTrueAndCategoryId(categoryId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProducts(String name) {
        return productRepository
                .findByNameContainingIgnoreCaseAndIsActiveTrue(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));
        return mapToDTO(product);
    }

    public ProductResponseDTO addProduct(
            String name,
            String description,
            BigDecimal price,
            BigDecimal discountPrice,
            Long categoryId,
            String brand,
            Integer quantity,
            MultipartFile image) throws IOException {

        String imageUrl = cloudinaryService.uploadImage(image);

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found"));

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .discountPrice(discountPrice)
                .imageUrl(imageUrl)
                .category(category)
                .brand(brand)
                .rating(0.0)
                .isActive(true)
                .build();

        Product saved = productRepository.save(product);

        Inventory inventory = Inventory.builder()
                .product(saved)
                .quantity(quantity)
                .reservedQty(0)
                .build();

        inventoryRepository.save(inventory);

        return mapToDTO(saved);
    }

    private ProductResponseDTO mapToDTO(Product product) {

        Integer availableStock = inventoryRepository
                .findByProductId(product.getId())
                .map(inv -> inv.getQuantity()
                        - inv.getReservedQty())
                .orElse(0);

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory() != null
                        ? product.getCategory().getName()
                        : null)
                .brand(product.getBrand())
                .rating(product.getRating())
                .availableStock(availableStock)
                .build();
    }
}
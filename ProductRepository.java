package com.Ecommerce.App.repository;

import com.Ecommerce.App.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();
    List<Product> findByIsActiveTrueAndCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(
            String name);
}
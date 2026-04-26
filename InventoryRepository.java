package com.Ecommerce.App.repository;

import com.Ecommerce.App.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity - :qty " +
            "WHERE i.product.id = :productId " +
            "AND i.quantity >= :qty")
    int deductStock(@Param("productId") Long productId,
                    @Param("qty") Integer qty);
}
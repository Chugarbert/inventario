package com.castores.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.castores.inventory.model.InventoryMovement;
import java.util.List;


public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findByType(String type);
}

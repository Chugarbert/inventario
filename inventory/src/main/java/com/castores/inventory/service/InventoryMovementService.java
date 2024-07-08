package com.castores.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.castores.inventory.model.InventoryMovement;
import com.castores.inventory.repository.InventoryMovementRepository;

import java.util.List;

@Service
public class InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public void saveMovement(InventoryMovement movement) {
        inventoryMovementRepository.save(movement);
    }

    public List<InventoryMovement> getAllMovements() {
        return inventoryMovementRepository.findAll();
    }

    public List<InventoryMovement> getMovementsByType(String type) {
        return inventoryMovementRepository.findByType(type);
    }
}

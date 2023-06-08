package com.dragmetall.repo;

import com.dragmetall.model.Equipment;
import com.dragmetall.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByCategory(Category category);
    List<Equipment> findAllByOrderByStatus();
}

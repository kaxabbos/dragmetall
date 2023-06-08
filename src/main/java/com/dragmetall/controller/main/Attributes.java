package com.dragmetall.controller.main;

import com.dragmetall.model.Equipment;
import com.dragmetall.model.Department;
import com.dragmetall.model.enums.Category;
import com.dragmetall.model.enums.Role;
import com.dragmetall.model.enums.Status;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("inStock", equipmentRepo.findAll().size());
    }

    protected void AddAttributesStats(Model model) {
        AddAttributes(model);

        List<Department> departments = departmentRepo.findAll();
        model.addAttribute("departments", departments);

        List<Equipment> equipments = equipmentRepo.findAll();
        int active = 0;
        int des = 0;

        for (Equipment i : equipments) {
            if (i.getStatus() == Status.ACTIVE) active++;
            else des++;
        }

        model.addAttribute("active", active);
        model.addAttribute("des", des);

        Category[] categories = Category.values();

        String[] names = new String[categories.length];
        int[] quantities = new int[categories.length];

        for (int i = 0; i < categories.length; i++) {
            names[i] = categories[i].getName();
            quantities[i] = equipmentRepo.findAllByCategory(categories[i]).size();
        }

        model.addAttribute("names", names);
        model.addAttribute("quantities", quantities);
    }

    protected void AddAttributesApps(Model model) {
        AddAttributes(model);
        model.addAttribute("apps", appsRepo.findAll());
    }


    protected void AddAttributesDepartment(Model model) {
        AddAttributes(model);
        model.addAttribute("departments", departmentRepo.findAll());
    }

    protected void AddAttributesDepartmentEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("department", departmentRepo.getReferenceById(id));
    }

    protected void AddAttributesEquipmentAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("departments", departmentRepo.findAll());
    }

    protected void AddAttributesEquipmentEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("departments", departmentRepo.findAll());
        model.addAttribute("equipment", equipmentRepo.getReferenceById(id));
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesEquipments(Model model) {
        AddAttributes(model);
        model.addAttribute("equipments", equipmentRepo.findAllByOrderByStatus());
        model.addAttribute("categories", Category.values());
        model.addAttribute("departments", departmentRepo.findAll());
    }

    protected void AddAttributesEquipmentsCategory(Model model, Category category) {
        AddAttributesEquipments(model);
        model.addAttribute("equipments", equipmentRepo.findAllByCategory(category));
        model.addAttribute("selectedCategory", category);
    }

    protected void AddAttributesEquipmentsDepartment(Model model, Long department) {
        AddAttributesEquipments(model);
        model.addAttribute("equipments", departmentRepo.getReferenceById(department).getEquipments());
        model.addAttribute("selectedW", department);
    }

    protected void AddAttributesEquipmentsSearch(Model model, Category category, String search, String desk, Long departmentId) {
        AddAttributesEquipments(model);

        List<Equipment> equipments = departmentRepo.getReferenceById(departmentId).getEquipments();
        equipments.sort(Comparator.comparing(Equipment::getQuantity));

        if (!desk.equals("cheap")) {
            Collections.reverse(equipments);
        }

        equipments = equipments.stream().filter(equipment -> equipment.getCategory() == category && equipment.getName().contains(search)).toList();

        model.addAttribute("equipments", equipments);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedW", departmentId);
        model.addAttribute("input", search);
    }
}

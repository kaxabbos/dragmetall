package com.dragmetall.controller;

import com.dragmetall.controller.main.Attributes;
import com.dragmetall.model.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/department")
public class DepartmentCont extends Attributes {
    @GetMapping
    String department(Model model) {
        AddAttributesDepartment(model);
        return "departments";
    }

    @GetMapping("/add")
    String departmentAdd(Model model) {
        AddAttributes(model);
        return "departmentAdd";
    }

    @PostMapping("/add")
    String equipmentAddNew(@RequestParam String name, @RequestParam String address, @RequestParam String description) {
        departmentRepo.save(new Department(name, address, description));
        return "redirect:/department";
    }

    @GetMapping("/edit/{id}")
    String departmentEdit(Model model, @PathVariable Long id) {
        AddAttributesDepartmentEdit(model, id);
        return "departmentEdit";
    }

    @PostMapping("/edit/{id}")
    String equipmentEditOld(@PathVariable Long id, @RequestParam String name, @RequestParam String address, @RequestParam String description) {
        Department department = departmentRepo.getReferenceById(id);
        department.setName(name);
        department.setAddress(address);
        department.setDescription(description);
        departmentRepo.save(department);
        return "redirect:/department";
    }

    @GetMapping("/delete/{id}")
    String departmentDelete(@PathVariable Long id) {
        departmentRepo.deleteById(id);
        return "redirect:/department";
    }

}

package com.dragmetall.controller;

import com.dragmetall.controller.main.Attributes;
import com.dragmetall.model.Equipment;
import com.dragmetall.model.Department;
import com.dragmetall.model.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/equipment")
public class EquipmentCont extends Attributes {
    @GetMapping("/all")
    String equipments(Model model) {
        AddAttributesEquipments(model);
        return "equipments";
    }

    @GetMapping("/category/{category}")
    String equipmentsCategory(Model model, @PathVariable Category category) {
        AddAttributesEquipmentsCategory(model, category);
        return "equipments";
    }

    @GetMapping("/department/{department}")
    String equipmentsDepartment(Model model, @PathVariable Long department) {
        AddAttributesEquipmentsDepartment(model, department);
        return "equipments";
    }

    @PostMapping("/search")
    String equipmentsSearch(Model model, @RequestParam Category category, @RequestParam Long departmentId, @RequestParam String search, @RequestParam String desk) {
        AddAttributesEquipmentsSearch(model, category, search, desk, departmentId);
        return "equipments";
    }

    @GetMapping("/add")
    String equipmentAdd(Model model) {
        AddAttributesEquipmentAdd(model);
        return "equipmentAdd";
    }

    @PostMapping("/add")
    String equipmentAddNew(Model model, @RequestParam MultipartFile img, @RequestParam String name, @RequestParam String provider, @RequestParam Category category, @RequestParam int quantity, @RequestParam String inv, @RequestParam String serial, @RequestParam Long department, @RequestParam double weight, @RequestParam String date, @RequestParam String description) {
        Equipment equipment = equipmentRepo.saveAndFlush(new Equipment(name, category, serial, quantity, weight, inv, date, description, provider));

        if (img != null && !Objects.requireNonNull(img.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + img.getOriginalFilename();
                    img.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                AddAttributesEquipmentAdd(model);
                return "equipmentAdd";
            }

            equipment.setImg(res);
            equipmentRepo.save(equipment);
        }

        Department wh = departmentRepo.getReferenceById(department);
        wh.addEquipment(equipment);
        departmentRepo.save(wh);
        equipmentRepo.save(equipment);
        return "redirect:/equipment/all";
    }

    @GetMapping("/edit/{id}")
    String equipmentEdit(Model model, @PathVariable Long id) {
        AddAttributesEquipmentEdit(model, id);
        return "equipmentEdit";
    }

    @PostMapping("/edit/{id}")
    String equipmentEditOld(@PathVariable Long id, Model model, @RequestParam MultipartFile img, @RequestParam String name, @RequestParam String provider, @RequestParam Category category, @RequestParam int quantity, @RequestParam String serial, @RequestParam Long department, @RequestParam double weight, @RequestParam String inv, @RequestParam String date, @RequestParam String description) {
        Equipment equipment = equipmentRepo.getReferenceById(id);

        equipment.setName(name);
        equipment.setDepartment(departmentRepo.getReferenceById(department));
        equipment.setProvider(provider);
        equipment.setCategory(category);
        equipment.setSerial(serial);
        equipment.setQuantity(quantity);
        equipment.setWeight(weight);
        equipment.setInv(inv);
        equipment.setDate(date);
        equipment.setDescription(description);

        if (img != null && !Objects.requireNonNull(img.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + img.getOriginalFilename();
                    img.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                AddAttributesEquipmentAdd(model);
                return "equipmentEdit";
            }

            equipment.setImg(res);
            equipmentRepo.save(equipment);
        }

        equipmentRepo.save(equipment);

        return "redirect:/equipment/all";
    }

    @GetMapping("/delete/{id}")
    String equipmentDelete(@PathVariable Long id) {
        equipmentRepo.deleteById(id);
        return "redirect:/equipment/all";
    }

}

package com.dragmetall.controller;

import com.dragmetall.controller.main.Attributes;
import com.dragmetall.model.Apps;
import com.dragmetall.model.Equipment;
import com.dragmetall.model.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apps")
public class AppsCont extends Attributes {
    @GetMapping()
    String app(Model model) {
        AddAttributesApps(model);
        return "apps";
    }

    @GetMapping("/conf/{id}")
    String UnConfirmation(@PathVariable Long id) {
        Equipment equipment = appsRepo.getReferenceById(id).getEquipment();
        appsRepo.deleteById(id);
        equipment.setStatus(Status.DISPOSED_OF);
        equipmentRepo.save(equipment);
        return "redirect:/apps";
    }
    @GetMapping("/add/{idEquipment}")
    String appAdd( @PathVariable Long idEquipment) {
        Equipment equipment = equipmentRepo.getReferenceById(idEquipment);
        equipment.addApp(new Apps( getUser()));
        equipmentRepo.save(equipment);
        return "redirect:/equipment/all";
    }
}

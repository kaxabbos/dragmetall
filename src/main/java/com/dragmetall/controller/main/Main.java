package com.dragmetall.controller.main;

import com.dragmetall.model.User;
import com.dragmetall.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Main {

    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected AppsRepo appsRepo;
    @Autowired
    protected EquipmentRepo equipmentRepo;
    @Autowired
    protected DepartmentRepo departmentRepo;


    @Value("${upload.img}")
    protected String uploadImg;

    protected User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return userRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }
}
package com.dragmetall.controller;

import com.dragmetall.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Attributes {
    @GetMapping
    String index1() {
        return "redirect:/equipment/all";
    }

    @GetMapping("/")
    String index2() {
        return "redirect:/equipment/all";
    }

    @GetMapping("/index")
    String index3() {
        return "redirect:/equipment/all";
    }
}

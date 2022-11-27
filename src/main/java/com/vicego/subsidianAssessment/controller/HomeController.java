package com.vicego.subsidianAssessment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {


    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping
    public String homepage() {
        return "index";
    }

}

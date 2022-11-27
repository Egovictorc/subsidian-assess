package com.vicego.subsidianAssessment.controller;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("api/file")
public class FileRestController {

    public String uploadFile(MultipartFile file) {
        return null;
    }


    public String downloadFile(String fileName) {
        return null;
    }

}

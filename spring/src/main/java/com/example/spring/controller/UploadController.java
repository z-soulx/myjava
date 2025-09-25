package com.example.spring.controller;

import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public String upload(@RequestBody String body) {
        System.out.println("Received body length: " + body.length());
        return "Received";
    }

    @PostMapping("/upload1")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Received file size: " + file.getSize());
        return "Received";
    }
}

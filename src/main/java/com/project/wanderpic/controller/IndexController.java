package com.project.wanderpic.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;




@Controller
public class IndexController {

    // application.properties 파일에 설정된 upload.dir 값을 주입
    @Value("${upload.dir}")
    private String uploadDir;

    
    @GetMapping("/")
    public String getMethodName() {
        return "index";
    }
    
    @PostMapping("/postImage")
    public String postMethodName(@RequestParam("file") MultipartFile file, Model model) {
        if(file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
        }
        
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            // 모델에 파일 경로 추가
            model.addAttribute("imageUrl", "/api/files/" + fileName);
            return "result";
        }
        catch(IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file.");
            return "index";
        }
    }
    
}

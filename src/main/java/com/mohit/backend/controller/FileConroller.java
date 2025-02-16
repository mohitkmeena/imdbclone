package com.mohit.backend.controller;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohit.backend.service.FIleService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileConroller {
    @Autowired
    private FIleService fileService;

   @Value("${project.poster}")
   private String path;

    @PostMapping("/upload")
    public ResponseEntity<String>uploadFile(@RequestPart MultipartFile file) throws IOException{

       String uploadedFileName= fileService.uploadFile(path, file);
        return ResponseEntity.ok("file uplaoded "+uploadedFileName);

    }
    @GetMapping(value = "/{fileName}")
    public void getFile(@PathVariable String fileName,HttpServletResponse res) throws IOException{
        InputStream ips=fileService.getResource(path, fileName);
        res.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(ips, res.getOutputStream());
    }

}

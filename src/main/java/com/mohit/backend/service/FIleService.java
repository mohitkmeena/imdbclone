package com.mohit.backend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FIleService {
   
    String uploadFile(String path,MultipartFile file) throws IOException;
    InputStream getResource(String path,String fileName) throws FileNotFoundException;
    
} 
package com.mohit.backend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileServiceImpl implements FIleService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //get name of file
       String name= file.getOriginalFilename();
       String filePath=path+File.separator+name;
       //create a file object
        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
        //copy file or upload
        Files.copy(file.getInputStream(), Paths.get( filePath),StandardCopyOption.REPLACE_EXISTING);
        return name; 


    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
       String filepath=path+File.separator+fileName;
       return new FileInputStream(filepath);
    }
    
}

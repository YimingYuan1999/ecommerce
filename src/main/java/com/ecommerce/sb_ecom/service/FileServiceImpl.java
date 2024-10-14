package com.ecommerce.sb_ecom.service;

import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException{
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.pathSeparator + fileName;
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

}

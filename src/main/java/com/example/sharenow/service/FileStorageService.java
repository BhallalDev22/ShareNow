package com.example.sharenow.service;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.utility.HandleFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private Path fileStoragePath;
    private String fileStorageLocation;

    public FileStorageService(@Value("${file.storage.location:temp}") String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    /*
    public String storeFile(MultipartFile file) {

//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getSize());
//        System.out.println(file.getContentType());
//        System.out.println(file.getName());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(fileStoragePath + "//" + fileName);

        //System.out.println(filePath);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Issue in storing file");
        }
        return fileName;
    }
    */

    public void storeFile(MultipartFile file, FileInfo fileInfo) {

        String fileName = fileInfo.getFileId() + fileInfo.getType();
        Path filePath = Paths.get(fileStoragePath + "//" + fileName);
        //System.out.println(filePath);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Issue in storing file");
        }
    }

    public void breakAndStore(MultipartFile file,FileInfo fileInfo) {
        HandleFile.breakAndStoreFile(file,fileInfo.getFileId());
    }

    public Resource downLoadFile(String uniqueFileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(uniqueFileName);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            // e.printStackTrace();
            throw new RuntimeException("Issue in reading file", e);
        }

        if(resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("File doesn't exist or is not readable");
        }
    }

    public Resource downloadFilePart(String fileId) {
        String downloadDir = "/home/aashish/Desktop/UploadDirectory" + "/" + fileId + "_1";
        Path path = Paths.get(downloadDir).toAbsolutePath();
        //System.out.println(path);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            // e.printStackTrace();
            throw new RuntimeException("Issue in reading file", e);
        }

        if(resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File doesn't exist or is not readable");
        }

    }
}

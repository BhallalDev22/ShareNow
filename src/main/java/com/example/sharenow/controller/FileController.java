package com.example.sharenow.controller;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.service.FileInfoStorageDbService;
import com.example.sharenow.service.FileStorageService;
import com.example.sharenow.utility.GetFileInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FileController {

    private FileInfoStorageDbService fileInfoStorageDbService;
    private FileStorageService fileStorageService;


    public FileController(FileInfoStorageDbService fileInfoStorageDbService,FileStorageService fileStorageService) {
        this.fileInfoStorageDbService = fileInfoStorageDbService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "active";
    }

    @GetMapping("/fileInfo")
    public List<FileInfo> getAllFiles() {
        return fileInfoStorageDbService.getAllFileInfo();
    }

    @GetMapping("/fileInfo/{ownerName}")
    List<FileInfo> getUserFilesInfo(@PathVariable("ownerName") String ownerName) {
        return fileInfoStorageDbService.getUserFileInfo(ownerName);
    }

 /*   @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestPart(value = "fileInfo") FileInfo fileInfo) {
        String fileName = fileStorageService.storeFile(file);

        // http://localhost:8081/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();
        String contentType = file.getContentType();

        fileInfoStorageDbService.addFileInfo(fileInfo);

        return "FILE UPLOADED SUCCESSFULLY";
    }
    */

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("filePair") MultipartFile[] filePair) {

        for(int i = 0;i<filePair.length;i+=2) {
            MultipartFile file = filePair[i];
            FileInfo fileInfo = GetFileInfo.getFileInfo(filePair[i+1]);

            fileStorageService.storeFile(filePair[i], fileInfo);
            fileStorageService.breakAndStore(filePair[i],fileInfo);

            String fileName = fileInfo.getFileId() + fileInfo.getType();

            // http://localhost:8081/download/abc.jpg
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileName)
                    .toUriString();
            String contentType = file.getContentType();

            fileInfoStorageDbService.addFileInfo(fileInfo);
        }

        return "FILE UPLOADED SUCCESSFULLY";
    }

    @GetMapping("/download/{fileId}")
    ResponseEntity<?> downloadFile(@PathVariable String fileId, HttpServletRequest httpServletRequest) {

        if(fileInfoStorageDbService.canBeDownloaded(fileId)) {
            Resource resource = fileStorageService.downLoadFile(fileId+fileInfoStorageDbService.findType(fileId));  // unique file name = fileId + file type

            String mimeType;
            try {
                mimeType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            fileInfoStorageDbService.updateNoOfTimes(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    //.contentType(contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename())
                    //.header(HttpHeaders.CONTENT_DISPOSITION,"inline;fileName=" + resource.getFilename())
                    .body(resource);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Maximum download limit reached or file has expired");

    }

    @GetMapping("/partDownload/{fileId}")
    ResponseEntity<?> partDownloadFile(@PathVariable String fileId, HttpServletRequest httpServletRequest) {

        if(fileInfoStorageDbService.canBeDownloaded(fileId)) {
            Resource resource = fileStorageService.downloadFilePart(fileId);

            String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            try {
                mimeType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            fileInfoStorageDbService.updateNoOfTimes(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    //.contentType(contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename())
                    //.header(HttpHeaders.CONTENT_DISPOSITION,"inline;fileName=" + resource.getFilename())
                    .body(resource);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Maximum download limit reached or file has expired");

    }

    @GetMapping("zipDownload")
    void zipDownload(@RequestParam("fileId") String[] fileIds, HttpServletResponse response) throws IOException {

        try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.asList(fileIds)
                    .stream()
                    .forEach(fileId -> {

                        String uniqueFileName = fileId + fileInfoStorageDbService.findType(fileId);
                        Resource resource = fileStorageService.downLoadFile(uniqueFileName);
                        ZipEntry zipEntry = new ZipEntry(resource.getFilename());

                        try {
                            zipEntry.setSize(resource.contentLength());
                            zos.putNextEntry(zipEntry);

                            StreamUtils.copy(resource.getInputStream(), zos);
                            zos.closeEntry();

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("some exception while zipping");
                        }
                    });
            zos.finish();
        }

        response.setStatus(200);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName=zipFile");
    }

    @PostMapping("/delete")
    public String deleteFiles(@RequestParam("uniqueFileNames") String[] uniqueFileNames) {
        for(String uniqueFileName : uniqueFileNames) {
            fileInfoStorageDbService.deleteFile(uniqueFileName.substring(0,uniqueFileName.lastIndexOf('.'))
                                                ,uniqueFileName.substring(uniqueFileName.lastIndexOf('.')));
        }
        return "FILES DELETED SUCCESSFULLY";
    }

}

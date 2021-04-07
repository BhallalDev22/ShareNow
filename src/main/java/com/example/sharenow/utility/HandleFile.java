package com.example.sharenow.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HandleFile {

    private static final String uploadDir = "/home/aashish/Desktop/UploadDirectory";
    private static final String downloadDir = "/home/aashish/Desktop/DownloadDirectory";

    public static void deleteFileFromStorage(String uniqueFileName) {
        String pathname = "/home/aashish/IntelliJ_projects/ShareNow/fileStorage/" + uniqueFileName ;
        // System.out.println(pathname);
        File file = new File(pathname);
        file.delete();
    }

    public static void deleteFileChunkFromStorage(String fileId) {
        String pathname = uploadDir+ "/" + fileId ;
        // System.out.println(pathname);
        File file = new File(pathname);
        file.delete();
    }

    public static void breakAndStoreFile(MultipartFile file, String fileId) {
        try {
            InputStream is = file.getInputStream();
            // System.out.println(is.available());
            byte data1[] = new byte[is.available() / 2];
            is.read(data1);
            // System.out.println(is.available());
            byte data2[] = new byte[is.available()];
            is.read(data2);
            // System.out.println(is.available());
            FileOutputStream fos1 = new FileOutputStream(uploadDir + "/" + fileId + "_1");
            FileOutputStream fos2 = new FileOutputStream(uploadDir + "/" + fileId + "_2");
            fos1.write(data1);
            fos2.write(data2);
            fos1.flush();
            fos1.close();
            fos2.flush();
            fos2.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mergeFiles(String fileId,String type) {
        try {
            FileInputStream fs1 = new FileInputStream(uploadDir + "/" + fileId + "_1");
            FileInputStream fs2 = new FileInputStream(uploadDir + "/" + fileId + "_2");
            byte data11[] = new byte[fs1.available()];
            byte data22[] = new byte[fs2.available()];
            fs1.read(data11);
            fs2.read(data22);
//            FileOutputStream fos = new FileOutputStream(uploadDir + "/" + "123.mp4");
            FileOutputStream fos = new FileOutputStream(downloadDir + "/" + fileId+type);
            fos.write(data11);
            fos.write(data22);
            fos.flush();
            fos.close();
            fs1.close();
            fs2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }

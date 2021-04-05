package com.example.sharenow.utility;

import com.example.sharenow.entity.FileInfo;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class GetFileInfo {

    public static FileInfo getFileInfo(MultipartFile multipartFile) {
        String info = null;
        try {
            info = new String(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        FileInfo fileInfo = gson.fromJson(info,FileInfo.class);
        return fileInfo;
    }

}

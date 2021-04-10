package com.example.sharenow.service;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.entity.UserData;
import com.example.sharenow.repository.FileRepository;
import com.example.sharenow.repository.UserDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.sharenow.utility.HandleFile.*;

@Service
public class ServerSync {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    FileRepository fileRepository;

    public static List<String> oldFileIdList;
    public static List<String> newFileIdList;

    public void getOldFileIdList() {
        oldFileIdList = new ArrayList<>();
        oldFileIdList.addAll(fileRepository.findFileId());
    }

    public boolean checkIfOtherServerIsActive() {
        try {
            URL url = new URL("http://localhost:8082/hello");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String responseLine = br.readLine();
            if(responseLine.equals("active"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sync(){

        if(checkIfOtherServerIsActive()) {
            userDataRepository.deleteAll();
            fileRepository.deleteAll();
        }

        try {
            URL url = new URL("http://localhost:8082/sync/userData");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = br.readLine();
                System.out.println(responseLine);
                Gson gson = new GsonBuilder().create();
                UserData[] userData = gson.fromJson(responseLine, UserData[].class);

                for(UserData user : userData){
                    userDataRepository.save(user);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL ("http://localhost:8082/sync/userFiles");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = br.readLine();

                Gson gson = new GsonBuilder().create();
                FileInfo[] fileInfo = gson.fromJson(responseLine, FileInfo[].class);

                for(FileInfo file : fileInfo){
                    fileRepository.save(file);
                    newFileIdList.add(file.getFileId());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateFilesInStorage() {
        for(String fileId : oldFileIdList) {
            if(!newFileIdList.contains(fileId)) {
                deleteFileFromStorage(fileId + fileRepository.findById(fileId).get().getType());
                deleteFileChunkFromStorage(fileId + "_1");
                deleteFileChunkFromStorage(fileId + "_2");
                oldFileIdList.remove(fileId);
            }
        }

        for(String fileId : newFileIdList) {
            if(!oldFileIdList.contains(fileId)) {
                getFile(fileId);
            }
        }
    }

    public void getFile(String fileId) {

        String fileName = fileId + fileRepository.findById(fileId).get().getType();
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {

        URL url = new URL("http://localhost:8082/download/" + fileId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();;
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);

        int status = connection.getResponseCode();

            if(status!=200){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while( (line = reader.readLine())!= null){
                    responseContent.append(line);
                }
                reader.close();
                System.out.println(responseContent);
            }
            else {
                File file = new File("/home/aashish/IntelliJ_projects/ShareNow/fileStorage/" + fileName);
                file.createNewFile();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = null;
                try {
                    is = url.openStream();
                    byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                    int n;

                    while ((n = is.read(byteChunk)) > 0) {
                        baos.write(byteChunk, 0, n);
                    }
                } catch (IOException e) {
                    System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                    e.printStackTrace();
                    // Perform any other exception handling that's appropriate.
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }

                byte[] data = baos.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
                fos.close();

                MultipartFile multipartFile = (MultipartFile) file;
                breakAndStoreFile(multipartFile, fileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

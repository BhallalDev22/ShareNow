package com.example.sharenow.service;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.entity.UserData;
import com.example.sharenow.repository.FileRepository;
import com.example.sharenow.repository.UserDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ServerSync {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    FileRepository fileRepository;

    public void sync(){

        userDataRepository.deleteAll();
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

        fileRepository.deleteAll();
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
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

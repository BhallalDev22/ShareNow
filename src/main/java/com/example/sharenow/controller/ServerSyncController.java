package com.example.sharenow.controller;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.entity.UserData;
import com.example.sharenow.repository.FileRepository;
import com.example.sharenow.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerSyncController {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    FileRepository fileRepository;

    @GetMapping(path = "/sync/userData")
    public Iterable<UserData> userDataSync () {
        return userDataRepository.findAll();
    }

    @GetMapping(path = "/sync/userFiles")
    public Iterable<FileInfo> fileInfoSync () {
        return fileRepository.findAll();
    }

}

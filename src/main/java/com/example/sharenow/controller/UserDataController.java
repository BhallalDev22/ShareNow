package com.example.sharenow.controller;

import com.example.sharenow.entity.UserData;
import com.example.sharenow.repository.UserDataRepository;
import com.example.sharenow.utility.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    @PostMapping(path = "/user/register")
    public Status addNewUser (@RequestBody UserData newUser) {
        List<UserData> userDataList = userDataRepository.findAll();
        System.out.println("New user" + newUser.toString());
        for(UserData userData : userDataList){
            if(userData.equals(newUser)){
                System.out.println("User Already Exists");
                return Status.USER_ALREADY_EXISTS;
            }
        }
        userDataRepository.save(newUser);
        return Status.SUCCESS;
    }

    @PostMapping("/user/login")
    public Status loginUser (@RequestBody UserData user) {
        List<UserData> userDataList = userDataRepository.findAll();

            for(UserData userData : userDataList){
                if(userData.equals(user)){
                    return Status.SUCCESS;
                }
            }

            return Status.FAILURE;
    }

    @GetMapping(path = "/user/allUsers")
    public Iterable<UserData> getAllUsers () {
        return userDataRepository.findAll();
    }
}

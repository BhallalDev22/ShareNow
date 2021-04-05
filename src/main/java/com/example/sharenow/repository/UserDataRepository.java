package com.example.sharenow.repository;

import com.example.sharenow.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, String> {

}

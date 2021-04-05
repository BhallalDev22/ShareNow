package com.example.sharenow.repositories;

import com.example.sharenow.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, String> {

}

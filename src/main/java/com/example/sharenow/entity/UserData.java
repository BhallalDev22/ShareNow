package com.example.sharenow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserData {

    @Id
    @Column(length = 100)
    private String userName;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String type;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData user = (UserData) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userName, password,
                type);
    }
    @Override
    public String toString() {
        return "UserData{" +
                ", UserName='" + userName + '\'' +
                ", Password='" + password + '\'' +
                ", Type=" + type +
                '}';
    }
}

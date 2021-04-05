package com.example.sharenow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "UserData", schema = "ShareNow")
public class UserData {

    @Id
    @Column(name = "UserName", length = 100)
    private String UserName;

    @Column(name = "Password", length = 100)
    private String Password;

    @Column(name = "Type", length = 50)
    private String Type;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData user = (UserData) o;
        return Objects.equals(UserName, user.UserName) &&
                Objects.equals(Password, user.Password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(UserName, Password,
                Type);
    }
    @Override
    public String toString() {
        return "UserData{" +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Type=" + Type +
                '}';
    }
}

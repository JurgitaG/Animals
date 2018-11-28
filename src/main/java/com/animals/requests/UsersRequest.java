package com.animals.requests;
import lombok.Data;

@Data
public class UsersRequest {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
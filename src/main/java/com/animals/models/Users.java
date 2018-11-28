package com.animals.models;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NonNull;

public class Users {
    @Id
    public ObjectId _id;

    @NonNull
    private String username;

    @NonNull
    @JsonIgnore
    private String password;

    private String token;

    public Users() {}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void set_id(ObjectId _id) { this._id = _id; }

    public String get_id() { return this._id.toHexString(); }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }

    public String getUsername() { return username; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
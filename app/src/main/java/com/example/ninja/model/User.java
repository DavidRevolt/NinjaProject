package com.example.ninja.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//SCHEMA
@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String full_name;
    private String password;
    private String email;
    private Long lastUpdated;


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("full_name", full_name);
        result.put("password", password);
        result.put("email", email);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }


    public void fromMap(Map<String, Object> map){
        id = (String)map.get("id");
        full_name = (String)map.get("full_name");
        password = (String)map.get("password");
        email = (String)map.get("email");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

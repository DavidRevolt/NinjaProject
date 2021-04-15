package com.example.ninja.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    private String id;
    private String userCreatorId;

    private String title;
    private String imgURL;
    private int prepTime;
    private int cookTime;
    private int totalTime;
    private int likesCounter;
    private String creationDate;

    private String applianceID;
    private String categoryID;
    private Long lastUpdated;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userCreatorId", userCreatorId);
        result.put("title", title);
        result.put("imgURL", imgURL);
        result.put("prepTime", prepTime);
        result.put("cookTime", cookTime);
        result.put("totalTime", cookTime+prepTime);
        result.put("likesCounter", likesCounter);
        result.put("creationDate", creationDate);
        result.put("applianceID", applianceID);
        result.put("categoryID", categoryID);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (String)map.get("id");
        userCreatorId = (String)map.get("userCreatorId");
        title = (String)map.get("title");
        imgURL = (String)map.get("imgURL");
        prepTime = (int)(long)map.get("prepTime");
        cookTime = (int)(long)map.get("cookTime");
        totalTime = (int)(long)map.get("totalTime");
        likesCounter = (int)(long)map.get("likesCounter");
        creationDate = (String)map.get("creationDate");
        applianceID = (String)map.get("applianceID");
        categoryID = (String)map.get("categoryID");
        Timestamp ts = (Timestamp) map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userID) {
        this.userCreatorId = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    public String getApplianceID() {
        return applianceID;
    }

    public void setApplianceID(String applianceID) {
        this.applianceID = applianceID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}

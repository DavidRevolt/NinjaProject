package com.example.ninja.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    private String id;
    private String userID;

    private String title;
    private String imgURL;
    private Duration prepTime;
    private Duration cookTime;
    private int likesCounter;
    private Date creationDate;

    private List<Appliance> appliances = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Duration getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Duration prepTime) {
        this.prepTime = prepTime;
    }

    public Duration getCookTime() {
        return cookTime;
    }

    public void setCookTime(Duration cookTime) {
        this.cookTime = cookTime;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    //APPLIANCES
    public List<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    public void addAppliance(Appliance app) {
        this.appliances.add(app);
    }

    public void delAppliance(Appliance app) {
        this.appliances.remove(app);
    }


    //Categories

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category cat) {
        this.categories.add(cat);
    }

    public void delCategory(Category cat) {
        this.categories.remove(cat);
    }
}

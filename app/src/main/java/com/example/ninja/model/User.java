package com.example.ninja.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String full_name;
    private String password;
    private String email;

    private List<Recipe> myFavorites = new ArrayList<>();
    private List<Recipe> myRecipes = new ArrayList<>();


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


    //FAVORITES
    public List<Recipe> getMyFavorites() {
        return myFavorites;
    }

    public void setMyFavorites(List<Recipe> myFavorites) {
        this.myFavorites = myFavorites;
    }

    public void addFavorite(Recipe favorite) {
        this.myFavorites.add(favorite);
    }

    public void delFavorite(Recipe favorite) {
        this.myFavorites.remove(favorite);
    }




    //USER OWN RECIPES
    public List<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(List<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
    }

    public void addMyRecipe(Recipe myNewRecipe) {
        this.myRecipes.add(myNewRecipe);
    }

    public void delMyRecipe(Recipe myNewRecipe) {
        this.myRecipes.remove(myNewRecipe);
    }
}

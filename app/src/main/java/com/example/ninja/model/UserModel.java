package com.example.ninja.model;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserModel {
    public final static UserModel instance = new UserModel();
    private UserModel(){}
    UserModelFirebase modelFirebase = new UserModelFirebase();
    UserModelSQL modelSql = new UserModelSQL();


    //GET ALL USER RECIPES
    MutableLiveData<List<Recipe>> userRecipes = new MutableLiveData<List<Recipe>>();
    public interface GetUserRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public MutableLiveData<List<Recipe>> getAllUserRecipes(){
        return userRecipes;
    }

    //Refresh UsersRecipes^^
    public interface refreshGetAllUserRecipesListener{
        void onComplete();
    }
    public void refreshGetAllUsers(String id , refreshGetAllUserRecipesListener listener){
        modelFirebase.getAllUserRecipes(id,new GetUserRecipesListener() {
            @Override
            public void onComplete(List<Recipe> data) {
                userRecipes.setValue(data);
                listener.onComplete();
            }
        });
    }


}
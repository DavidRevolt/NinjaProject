package com.example.ninja.model;

import java.util.List;

public class UserModel {
    public final static UserModel instance = new UserModel();
    private UserModel(){}
    UserModelFirebase modelFirebase = new UserModelFirebase();
    UserModelSQL modelSql = new UserModelSQL();


    public interface DelUserListener{
        void onComplete();
    }
    public void delUser(final User user, final DelUserListener listener){
        modelFirebase.delUser(user,listener);
    }


    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(final User user, final AddUserListener listener){
        modelFirebase.addUser(user,listener);
    }


    public interface UpdateUserListener extends AddUserListener {
        void onComplete();
    }
    public void updateUser(final User user, final UpdateUserListener listener){
        modelFirebase.updateUser(user,listener);
    }


    public interface GetAllUsers{
        void onComplete(List<User> data);
    }
    public void GetAllUsers(final GetAllUsers listener){
        modelFirebase.GetAllUsers(listener);
    }


    public interface GetUserRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public void getAllUserRecipes(final String id, final GetUserRecipesListener listener){
        modelFirebase.getAllUserRecipes(id,listener);
    }

    public interface GetUserListener{
        void onComplete(User user);
    }
    public void getUser(String id, GetUserListener listener){
        modelFirebase.getUser( id,  listener);
    }

}

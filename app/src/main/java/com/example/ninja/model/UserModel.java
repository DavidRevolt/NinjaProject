package com.example.ninja.model;

import androidx.lifecycle.MutableLiveData;

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




    //GET ALL USERS
    MutableLiveData<List<User>> usersList = new MutableLiveData<List<User>>();
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public MutableLiveData<List<User>> GetAllUsers(){
        return usersList;
    }

    //Refresh UsersList^^
    public interface refreshGetAllUsersListener{
        void onComplete();
    }
    public void refreshGetAllUsers(refreshGetAllUsersListener listener){
        modelFirebase.GetAllUsers(new GetAllUsersListener() {
            @Override
            public void onComplete(List<User> data) {
                usersList.setValue(data);
                listener.onComplete();
            }
        });
    }




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




    //GET USER
    MutableLiveData<User> user = new MutableLiveData<User>();
    public interface GetUserListener{
        void onComplete(User user);
    }
    public MutableLiveData<User> getUser(String id){
        modelFirebase.getUser(id, new GetUserListener() {
            @Override
            public void onComplete(User data) {
                user.setValue(data);
            }
        });
        return user;
    }

}

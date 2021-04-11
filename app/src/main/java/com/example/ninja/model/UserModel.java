package com.example.ninja.model;


import android.os.AsyncTask;

import java.util.List;

//OPERATION ON USER CLASS
public class UserModel {
    public final static UserModel instance = new UserModel();
    private UserModel(){}


    public interface DelUserListener{
        void onComplete();
    }
    public void delUser(final User user, final DelUserListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.userDao().delete(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(final User user, final AddUserListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.userDao().insertAll(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface UpdateUserListener{
        void onComplete();
    }
    public void updateUser(final User user, final UpdateUserListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.userDao().updateUsers(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }


    public interface GetUserRecipesListener{
        void onComplete(List<UserWithRecipes> data);
    }
    public void getAllUserRecipes(final String id, final GetUserRecipesListener listener){
        class MyAsyncTask extends AsyncTask{
            List<UserWithRecipes> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.userDao().getUsersWithRecipes(id);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}

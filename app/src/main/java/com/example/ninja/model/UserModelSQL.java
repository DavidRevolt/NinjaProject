package com.example.ninja.model;


import android.os.AsyncTask;

import java.util.List;

//OPERATION ON USER CLASS
public class UserModelSQL {


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

package com.example.ninja.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryModelSQL {


    public interface DelCategoryListener{
        void onComplete();
    }
    public void delCategory(final Category category, final DelCategoryListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.categoryDao().delete(category);
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

    public interface AddCategoryListener{
        void onComplete();
    }
    public void addCategory(final Category category, final AddCategoryListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.categoryDao().insertAll(category);
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

    public interface UpdateCategoryListener{
        void onComplete();
    }
    public void updateCategory(final Category category, final UpdateCategoryListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.categoryDao().updateCategory(category);
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


    public LiveData<List<Category>> getAllCategories(){
        return AppLocalDb.db.categoryDao().getAll();
    }

    public LiveData<Category> getCategory(String id){
        return AppLocalDb.db.categoryDao().getCategory(id);
    }



}

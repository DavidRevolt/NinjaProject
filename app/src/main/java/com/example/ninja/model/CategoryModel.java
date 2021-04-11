package com.example.ninja.model;

import android.os.AsyncTask;

import java.util.List;

public class CategoryModel {
    public final static CategoryModel instance = new CategoryModel();
    private CategoryModel(){}

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


    public interface GetCategoryRecipesListener{
        void onComplete(List<CategoryWithRecipes> data);
    }
    public void getAllCategoryRecipes(final String id, final GetCategoryRecipesListener listener){
        class MyAsyncTask extends AsyncTask{
            List<CategoryWithRecipes> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.categoryDao().getCategoryWithRecipes(id);
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

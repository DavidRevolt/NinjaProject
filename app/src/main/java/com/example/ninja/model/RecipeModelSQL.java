package com.example.ninja.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeModelSQL {


    public interface DelRecipeListener{
        void onComplete();
    }
    public void delRecipe(final Recipe recipe, final DelRecipeListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.recipeDao().delete(recipe);
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

    public interface AddRecipeListener{
        void onComplete();
    }
    public void addRecipe(final Recipe recipe, final AddRecipeListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.recipeDao().insertAll(recipe);
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

    public interface UpdateRecipeListener{
        void onComplete();
    }
    public void updateRecipe(final Recipe recipe, final UpdateRecipeListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.recipeDao().updateRecipe(recipe);
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


    public LiveData<List<Recipe>> getAllRecipes(){
        return AppLocalDb.db.recipeDao().getAll();
    }

    public LiveData<Recipe> getRecipe(String id){
        return AppLocalDb.db.recipeDao().getRecipe(id);
    }

}

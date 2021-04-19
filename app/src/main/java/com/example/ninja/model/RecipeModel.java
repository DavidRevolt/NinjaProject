package com.example.ninja.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ninja.MyApplication;
import com.google.firebase.firestore.util.Listener;

import java.util.List;

public class RecipeModel {
    public final static RecipeModel instance = new RecipeModel();
    private RecipeModel(){}
    RecipeModelFirebase modelFirebase = new RecipeModelFirebase();
    RecipeModelSQL modelSql = new RecipeModelSQL();


    public interface DelRecipeListener{
        void onComplete();
    }
    public void delRecipe(final Recipe recipe, final DelRecipeListener listener){
        modelFirebase.delRecipe(recipe,listener);
        modelSql.delRecipe(recipe, new RecipeModelSQL.DelRecipeListener() {
            @Override
            public void onComplete() {
                refreshGetAllRecipes(new RefreshGetAllRecipesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

            }
        });
    }


    public interface AddRecipeListener{
        void onComplete();
    }
    public void addRecipe(final Recipe recipe, final AddRecipeListener listener){
        modelFirebase.addRecipe(recipe, new AddRecipeListener() {
            @Override
            public void onComplete() {
                refreshGetAllRecipes(new RefreshGetAllRecipesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }


    public interface UpdateRecipeListener extends AddRecipeListener {
        void onComplete();
    }
    public void updateRecipe(final Recipe recipe, final UpdateRecipeListener listener){
        modelFirebase.updateRecipe(recipe, new UpdateRecipeListener() {
            @Override
            public void onComplete() {
                refreshGetAllRecipes(new RefreshGetAllRecipesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }



    //
    //Get All Recipes In Firebase
    LiveData<List<Recipe>> recipesList;
    public interface GetAllRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public LiveData<List<Recipe>> GetAllRecipes(){
        if (recipesList == null){
            recipesList = modelSql.getAllRecipes();
            refreshGetAllRecipes(null);
        }
        return recipesList;
    }

    //Refresh RecipesList^^
    public interface RefreshGetAllRecipesListener{
        void onComplete();
    }
    public void refreshGetAllRecipes(RefreshGetAllRecipesListener listener){
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedRecipeList",0);

        modelFirebase.getAllRecipes(lastUpdated, new GetAllRecipesListener() {
            @Override
            public void onComplete(List<Recipe> data) {
                long lastUp = 0;
                for (Recipe rec: data) {
                    modelSql.addRecipe(rec,null);
                    if (rec.getLastUpdated()>lastUp){
                        lastUp = rec.getLastUpdated();
                    }
                }
                sp.edit().putLong("lastUpdatedRecipes", lastUp).commit();
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }




    //Get Recipe
    LiveData<Recipe> recipe;
    public interface GetRecipeListener{
        void onComplete(Recipe recipe);
    }
    public LiveData<Recipe> getRecipe(String id){
        recipe = modelSql.getRecipe(id);

        if (recipe == null)
            refreshGetRecipe(id, null);
        return recipe;
    }


    //Refresh Recipe^^
    public interface RefreshGetRecipeListener{
        void onComplete();
    }
    public void refreshGetRecipe(String id, RefreshGetRecipeListener listener){
        modelFirebase.getRecipe(id, new GetRecipeListener() {
            @Override
            public void onComplete(Recipe data) {
                modelSql.addRecipe(data,null);
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }



    public interface UploadImageListener{
        void onComplete(String url);
    }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }
}

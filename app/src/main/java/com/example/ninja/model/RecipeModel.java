package com.example.ninja.model;

import android.content.Context;
import android.content.SharedPreferences;

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
    }


    public interface AddRecipeListener{
        void onComplete();
    }
    public void addRecipe(final Recipe recipe, final AddRecipeListener listener){
        modelFirebase.addRecipe(recipe, new AddRecipeListener() {
            @Override
            public void onComplete() {
                refreshGetAllRecipes(new refreshGetAllRecipesListener() {
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
        modelFirebase.updateRecipe(recipe,listener);
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
    public interface refreshGetAllRecipesListener{
        void onComplete();
    }
    public void refreshGetAllRecipes(refreshGetAllRecipesListener listener){
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedRecipeList",0);

        modelFirebase.GetAllRecipes(lastUpdated, new GetAllRecipesListener() {
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
    MutableLiveData<Recipe> recipe = new MutableLiveData<Recipe>();
    public interface GetRecipeListener{
        void onComplete(Recipe recipe);
    }
    public MutableLiveData<Recipe> getRecipe(String id){
        modelFirebase.getRecipe(id, new GetRecipeListener() {
            @Override
            public void onComplete(Recipe data) {
                recipe.setValue(data);
            }
        });
        return recipe;
    }

}

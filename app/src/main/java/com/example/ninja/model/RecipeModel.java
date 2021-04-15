package com.example.ninja.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
        modelFirebase.addRecipe(recipe,listener);
    }


    public interface UpdateRecipeListener extends AddRecipeListener {
        void onComplete();
    }
    public void updateRecipe(final Recipe recipe, final UpdateRecipeListener listener){
        modelFirebase.updateRecipe(recipe,listener);
    }



    //
    //Get All Recipes In Firebase
    MutableLiveData<List<Recipe>> recipesList = new MutableLiveData<List<Recipe>>();
    public interface GetAllRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public MutableLiveData<List<Recipe>> GetAllRecipes(){
        return recipesList;
    }

    //Refresh RecipesList^^
    public interface refreshGetAllRecipesListener{
        void onComplete();
    }
    public void refreshGetAllRecipes(refreshGetAllRecipesListener listener){
        modelFirebase.GetAllRecipes(new GetAllRecipesListener() {
            @Override
            public void onComplete(List<Recipe> data) {
                recipesList.setValue(data);
                listener.onComplete();
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

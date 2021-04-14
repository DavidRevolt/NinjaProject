package com.example.ninja.model;

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


    public interface GetAllRecipes{
        void onComplete(List<Recipe> data);
    }
    public void GetAllRecipes(final GetAllRecipes listener){
        modelFirebase.GetAllRecipes(listener);
    }


    public interface GetRecipeListener{
        void onComplete(Recipe recipe);
    }
    public void getRecipe(String id, GetRecipeListener listener){
        modelFirebase.getRecipe( id,  listener);
    }

}

package com.example.ninja.ui.browse;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;

import java.util.List;

public class BrowseViewModel extends ViewModel {
    //Data
    private LiveData<List<Recipe>> recipeList;

    public BrowseViewModel() {
        recipeList = RecipeModel.instance.GetAllRecipes();

    }
    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
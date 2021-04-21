package com.example.ninja.ui.home;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private LiveData<List<Recipe>> recipeList;


    public HomeViewModel() {
        recipeList = RecipeModel.instance.GetAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
package com.example.ninja.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;

import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    //Data
    private MutableLiveData<List<Recipe>> recipeList;

    //constructor
    public HomeViewModel() {
        recipeList = RecipeModel.instance.GetAllRecipes();
    }

    public MutableLiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
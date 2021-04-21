package com.example.ninja.ui.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipeList;

    public ProfileViewModel() {
        this.recipeList = RecipeModel.instance.GetAllUserRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
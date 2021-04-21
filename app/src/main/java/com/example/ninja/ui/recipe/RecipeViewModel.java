package com.example.ninja.ui.recipe;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.ninja.model.Appliance;
import com.example.ninja.model.ApplianceModel;
import com.example.ninja.model.Category;
import com.example.ninja.model.CategoryModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import java.util.List;


public class RecipeViewModel extends ViewModel {

    private LiveData<Recipe> recipe;
    private LiveData<List<Category>> categoryList;
    private LiveData<List<Appliance>> applianceList;
    private String id;


    public RecipeViewModel( ) {
        categoryList = CategoryModel.instance.GetAllCategories();
        applianceList = ApplianceModel.instance.GetAllAppliances();
    }


    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }


    public LiveData<List<Appliance>> getApplianceList() {
        return applianceList;
    }


    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipeId(String id) {
        this.id = id;
        recipe = RecipeModel.instance.getRecipe(id);
    }

}
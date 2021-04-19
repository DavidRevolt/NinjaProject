package com.example.ninja.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ninja.model.Appliance;
import com.example.ninja.model.ApplianceModel;
import com.example.ninja.model.Category;
import com.example.ninja.model.CategoryModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;

import java.util.List;

public class CreateViewModel extends ViewModel {
    private LiveData<List<Category>> categoryList;
    private LiveData<List<Appliance>> applianceList;


    public CreateViewModel() {
        categoryList = CategoryModel.instance.GetAllCategories();
        applianceList = ApplianceModel.instance.GetAllAppliances();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    public LiveData<List<Appliance>> getApplianceList() {
        return applianceList;
    }

}
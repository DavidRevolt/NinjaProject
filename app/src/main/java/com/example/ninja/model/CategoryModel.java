package com.example.ninja.model;

import android.os.AsyncTask;

import java.util.List;

public class CategoryModel {
    public final static CategoryModel instance = new CategoryModel();
    private CategoryModel(){}
    CategoryModelFirebase modelFirebase = new CategoryModelFirebase();
    CategoryModelSQL modelSql = new CategoryModelSQL();


    public interface DelCategoryListener{
        void onComplete();
    }
    public void delCategory(final Category category, final DelCategoryListener listener){
        modelFirebase.delCategory(category,listener);
    }


    public interface AddCategoryListener{
        void onComplete();
    }
    public void addCategory(final Category category, final AddCategoryListener listener){
        modelFirebase.addCategory(category,listener);
    }


    public interface UpdateCategoryListener extends AddCategoryListener {
        void onComplete();
    }
    public void updateCategory(final Category category, final UpdateCategoryListener listener){
        modelFirebase.updateCategory(category,listener);
    }


    public interface GetAllCategories{
        void onComplete(List<Category> data);
    }
    public void GetAllCategories(final GetAllCategories listener){
        modelFirebase.GetAllCategories(listener);
    }


    public interface GetCategoryRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public void getAllCategoryRecipes(final String id, final GetCategoryRecipesListener listener){
        modelFirebase.getAllCategoryRecipes(id,listener);
    }

    public interface GetCategoryListener{
        void onComplete(Category category);
    }
    public void getCategory(String id, GetCategoryListener listener){
        modelFirebase.getCategory( id,  listener);
    }

}

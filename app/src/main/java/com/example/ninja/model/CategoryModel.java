package com.example.ninja.model;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

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



    //GET ALL CATEGORIES
    MutableLiveData<List<Category>> categoriesList = new MutableLiveData<List<Category>>();
    public interface GetAllCategoriesListener {
        void onComplete(List<Category> data);
    }
    public MutableLiveData<List<Category>> GetAllCategories(){
        return categoriesList;
    }

    //Refresh CategoriesList^^
    public interface refreshGetAllCategoriesListener{
        void onComplete();
    }
    public void refreshGetAllCategories(refreshGetAllCategoriesListener listener){
        modelFirebase.GetAllCategories(new GetAllCategoriesListener() {
            @Override
            public void onComplete(List<Category> data) {
                categoriesList.setValue(data);
                listener.onComplete();
            }
        });
    }





    //GET ALL Category RECIPES
    MutableLiveData<List<Recipe>> categoryRecipes = new MutableLiveData<List<Recipe>>();
    public interface GetCategoryRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public MutableLiveData<List<Recipe>> getAllCategoryRecipes(final String id){
        return categoryRecipes;
    }

    //Refresh CategoryRecipes^^
    public interface refreshGetAllCategoryRecipesListener{
        void onComplete();
    }
    public void refreshGetAllUsers(String id , refreshGetAllCategoryRecipesListener listener){
        modelFirebase.getAllCategoryRecipes(id,new GetCategoryRecipesListener() {
            @Override
            public void onComplete(List<Recipe> data) {
                categoryRecipes.setValue(data);
                listener.onComplete();
            }
        });
    }




    //Get Category
    MutableLiveData<Category> category = new MutableLiveData<Category>();
    public interface GetCategoryListener{
        void onComplete(Category category);
    }
    public MutableLiveData<Category> getCategory(String id){
        modelFirebase.getCategory(id, new GetCategoryListener() {
            @Override
            public void onComplete(Category data) {
                category.setValue(data);
            }
        });
        return category;
    }

}

package com.example.ninja.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ninja.MyApplication;

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
        modelSql.delCategory(category, new CategoryModelSQL.DelCategoryListener() {
            @Override
            public void onComplete() {
                refreshGetAllCategories(new RefreshGetAllCategoriesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

            }
        });
    }


    public interface AddCategoryListener{
        void onComplete();
    }
    public void addCategory(final Category category, final AddCategoryListener listener){
        modelFirebase.addCategory(category, new CategoryModel.AddCategoryListener() {
            @Override
            public void onComplete() {
                refreshGetAllCategories(new RefreshGetAllCategoriesListener(){
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }


    public interface UpdateCategoryListener extends AddCategoryListener {
        void onComplete();
    }
    public void updateCategory(final Category category, final UpdateCategoryListener listener){
        modelFirebase.updateCategory(category, new CategoryModel.UpdateCategoryListener() {
            @Override
            public void onComplete() {
                refreshGetAllCategories(new RefreshGetAllCategoriesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }




    //GET ALL CATEGORIES
    LiveData<List<Category>> categoriesList;
    public interface GetAllCategoriesListener {
        void onComplete(List<Category> data);
    }
    public LiveData<List<Category>> GetAllCategories(){
        if (categoriesList == null){
            categoriesList = modelSql.getAllCategories();
            refreshGetAllCategories(null);
        }
        return categoriesList;
    }



    //Refresh CategoriesList^^
    public interface RefreshGetAllCategoriesListener{
        void onComplete();
    }

    public void refreshGetAllCategories(RefreshGetAllCategoriesListener listener){
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedCategoryList",0);

        modelFirebase.getAllCategories(lastUpdated, new GetAllCategoriesListener() {
            @Override
            public void onComplete(List<Category> data) {
                long lastUp = 0;
                for (Category rec: data) {
                    modelSql.addCategory(rec,null);
                    if (rec.getLastUpdated()>lastUp){
                        lastUp = rec.getLastUpdated();
                    }
                }
                sp.edit().putLong("lastUpdatedCategoryList", lastUp).commit();
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }




    //GET CATEGORY
    LiveData<Category> category;
    public interface GetCategoryListener{
        void onComplete(Category category);
    }
    public LiveData<Category> getCategory(String id){
        category = modelSql.getCategory(id);
        refreshGetCategory(id, null);
        return category;
    }

    //Refresh Category^^
    public interface RefreshGetCategoryListener{
        void onComplete();
    }
    public void refreshGetCategory(String id, RefreshGetCategoryListener listener){
        modelFirebase.getCategory(id, new GetCategoryListener() {
            @Override
            public void onComplete(Category data) {
                modelSql.addCategory(data,null);
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }
}

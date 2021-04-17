package com.example.ninja.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface RecipeDao {
    @Delete
    void delete(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipe);

    @Update
    public void updateRecipe(Recipe... recipe);

    @Query("select * from Recipe")
    public LiveData<List<Recipe>> getAll();

    @Query("SELECT * FROM Recipe where Recipe.id = :id")
    public LiveData<Recipe> getRecipe(String id);
}

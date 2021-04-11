package com.example.ninja.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface CategoryDao {
    @Delete
    void delete(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... category);

    @Update
    public void updateRecipe(Category... category);

    @Query("select * from Category")
    List<Category> getAll();
}

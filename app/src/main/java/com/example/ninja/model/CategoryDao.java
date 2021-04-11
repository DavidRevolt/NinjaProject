package com.example.ninja.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
@Dao
public interface CategoryDao {
    @Delete
    void delete(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... category);

    @Update
    public void updateCategory(Category... category);

    @Query("select * from Category")
    List<Category> getAll();

    @Transaction
    @Query("SELECT * FROM Category where Category.id = :id")
    public List<CategoryWithRecipes> getCategoryWithRecipes(String id);
}

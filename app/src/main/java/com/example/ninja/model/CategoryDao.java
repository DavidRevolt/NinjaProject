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
public interface CategoryDao {
    @Delete
    void delete(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... category);

    @Update
    public void updateCategory(Category... category);

    @Query("select * from Category")
    public LiveData<List<Category>> getAll();

    @Query("SELECT * FROM Category where Category.id = :id")
    public LiveData<Category> getCategory(String id);
}

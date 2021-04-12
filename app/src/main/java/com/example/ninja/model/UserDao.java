package com.example.ninja.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

//DB OPERATION
@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... user);

    @Update
    public void updateUsers(User... users);

    @Transaction
    @Query("SELECT * FROM User where User.id = :id")
    public List<UserWithRecipes> getUsersWithRecipes(String id);


}

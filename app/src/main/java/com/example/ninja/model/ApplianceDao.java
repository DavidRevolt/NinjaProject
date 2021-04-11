package com.example.ninja.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface ApplianceDao {
    @Delete
    void delete(Appliance appliance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Appliance... appliance);

    @Update
    public void updateRecipe(Appliance... appliance);

    @Query("select * from Appliance")
    List<Appliance> getAll();
}

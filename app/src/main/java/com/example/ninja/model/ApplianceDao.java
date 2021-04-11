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
public interface ApplianceDao {
    @Delete
    void delete(Appliance appliance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Appliance... appliance);

    @Update
    public void updateAppliance(Appliance... appliance);

    @Query("select * from Appliance")
    List<Appliance> getAll();

    @Transaction
    @Query("SELECT * FROM Appliance where Appliance.id = :id")
    public List<ApplianceWithRecipes> getApplianceWithRecipes(String id);
}

package com.example.ninja.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ApplianceWithRecipes {
    @Embedded
    public Appliance appliance;
    @Relation(
            parentColumn = "id",
            entityColumn = "applianceID"
    )
    public List<Recipe> recipes;
}

package com.example.ninja.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithRecipes {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "categoryID"
    )
    public List<Recipe> recipes;
}

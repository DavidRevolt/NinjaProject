package com.example.ninja.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;


public class UserWithRecipes {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userCreatorId"
    )
    public List<Recipe> recipes;
}

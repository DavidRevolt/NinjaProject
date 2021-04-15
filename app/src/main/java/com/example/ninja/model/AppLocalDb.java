package com.example.ninja.model;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ninja.MyApplication;

//DB MANGER, update version after every change!

@Database(entities = {Appliance.class, Category.class, Recipe.class, User.class}, version = 5)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ApplianceDao applianceDao();
    public abstract CategoryDao categoryDao();
    public abstract RecipeDao recipeDao();
    public abstract UserDao userDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "myDB1.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
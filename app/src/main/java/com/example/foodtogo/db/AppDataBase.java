package com.example.foodtogo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodtogo.model.Meal;



@Database(entities = {Meal.class},version = 3)
public abstract class AppDataBase extends RoomDatabase {


    private static AppDataBase instance = null ;
    public abstract MealDAO getMealDAO();
    public static synchronized AppDataBase getInstance(Context context)
    {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"productsdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }

}

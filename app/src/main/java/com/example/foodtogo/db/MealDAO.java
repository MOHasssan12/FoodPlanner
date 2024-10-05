package com.example.foodtogo.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodtogo.model.Meal;

import java.util.List;
@Dao
public interface MealDAO {


    @Query("SELECT * FROM meals_table")
    LiveData<List<Meal>> getPojos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);


    @Query("SELECT * FROM meals_table WHERE strMeal = :mealName LIMIT 1")
    LiveData<Meal> getMealByName(String mealName);

    @Query("SELECT * FROM meals_table WHERE isPlanned = 1 AND planDate = :date")
    LiveData<List<Meal>> getMealsForDate(String date);



}

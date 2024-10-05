package com.example.foodtogo.Meal.View;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface MealView  {

    public void showData(List<Meal> meal);
    public void showDataForFavMeal(LiveData<Meal> meal);
    public void showErrMsg(String error);

}

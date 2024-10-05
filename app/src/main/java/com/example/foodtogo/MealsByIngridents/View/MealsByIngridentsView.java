package com.example.foodtogo.MealsByIngridents.View;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface MealsByIngridentsView {


    public void showData(List<Meal> meals);

    public void showErrMsg(String error);

}

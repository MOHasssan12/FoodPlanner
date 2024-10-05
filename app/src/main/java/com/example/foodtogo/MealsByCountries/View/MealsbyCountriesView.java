package com.example.foodtogo.MealsByCountries.View;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface MealsbyCountriesView {

    public void showData(List<Meal> meals);
    public void showErrMsg(String error);


}

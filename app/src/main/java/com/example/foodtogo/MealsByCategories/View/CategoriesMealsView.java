package com.example.foodtogo.MealsByCategories.View;

import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Meal;

import java.util.List;

public interface CategoriesMealsView {


    public void showData(List<Meal> meals);

    public void showErrMsg(String error);

}

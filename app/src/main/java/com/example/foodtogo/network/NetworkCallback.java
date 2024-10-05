package com.example.foodtogo.network;

import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;

import java.util.List;

public interface NetworkCallback {

    public void onSuccessResult(List<Meal> Meal);
    public void onSuccessResultCate(List<Category> categories);
    public void onSuccessResultCountry(List<Country> countries);

    public void onSuccessResultIngridents(List<Ingridents> ingridents);

    public void onFailureResult(String errorMsg);



}

package com.example.foodtogo.Home.View;

import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;

import java.util.List;

public interface HomeFragView {

    public void showData(List<Meal> meals);
    public void showDataCate(List<Category> categories);
    public void showDataIngridents(List<Ingridents> ingridents);
    public void showDataCountries(List<Country> countries);
    public void showErrMsg(String error);

}

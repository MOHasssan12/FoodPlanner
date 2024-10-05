package com.example.foodtogo.Fav.View;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface FavView {

    public void showData(List<Meal> meal);
    public void showErrMsg(String error);

}

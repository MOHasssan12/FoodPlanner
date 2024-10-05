package com.example.foodtogo.Search.View;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface SearchFragmentView {

    public void showData(List<Meal> meal);
    public void showErrMsg(String error);

}

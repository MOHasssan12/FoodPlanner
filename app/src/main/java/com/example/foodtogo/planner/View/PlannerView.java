package com.example.foodtogo.planner.View;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.model.Meal;

import java.util.List;

public interface PlannerView {

    public void showData(LiveData<List<Meal>> meals);
    public void showErrMsg(String error);


}


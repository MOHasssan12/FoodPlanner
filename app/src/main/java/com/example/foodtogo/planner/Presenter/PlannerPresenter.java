package com.example.foodtogo.planner.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.MealsByCountries.View.MealsbyCountriesView;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.planner.View.PlannerView;

import java.util.List;

public class PlannerPresenter {


    private PlannerView _view;
    private Repo _repo;

    public PlannerPresenter(PlannerView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public void deleteFavMeal(Meal meal){
        _repo.deleteMeal(meal);
    }
    public void insertMeal(Meal meal){
        _repo.insertMeal(meal);
    }

    public void getMealsForDate(String date){
        LiveData<List<Meal>>  meals =   _repo.getMealsForDate(date);
        _view.showData(meals);
    }



}

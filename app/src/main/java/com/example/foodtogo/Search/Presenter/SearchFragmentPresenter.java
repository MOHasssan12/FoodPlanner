package com.example.foodtogo.Search.Presenter;

import android.util.Log;

import com.example.foodtogo.Meal.View.MealView;
import com.example.foodtogo.Search.View.SearchFragmentView;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.NetworkCallback;

import java.util.List;

public class SearchFragmentPresenter implements NetworkCallback {

    private SearchFragmentView _view;
    private Repo _repo;

    public SearchFragmentPresenter(SearchFragmentView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public void getMeals(String quarySearch){
        _repo.getMealByName(quarySearch,this);
    }


    @Override
    public void onSuccessResult(List<Meal> Meal) {
        _view.showData(Meal);

    }

    @Override
    public void onSuccessResultCate(List<Category> categories) {

    }

    @Override
    public void onSuccessResultCountry(List<Country> countries) {

    }

    @Override
    public void onSuccessResultIngridents(List<Ingridents> ingridents) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }
}

package com.example.foodtogo.MealsByCountries.Presenter;

import com.example.foodtogo.MealsByCategories.View.CategoriesMealsView;
import com.example.foodtogo.MealsByCountries.View.MealsbyCountriesView;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.NetworkCallback;

import java.util.List;

public class MealsbyCountriesPresenter implements NetworkCallback {


    private MealsbyCountriesView _view;
    private Repo _repo;




    public MealsbyCountriesPresenter(MealsbyCountriesView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public void getMeals(String categoryName){
        _repo.getMealsByCountries(categoryName,this);
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

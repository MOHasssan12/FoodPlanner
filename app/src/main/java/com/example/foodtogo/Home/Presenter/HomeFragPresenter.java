package com.example.foodtogo.Home.Presenter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import com.example.foodtogo.Home.View.HomeFragView;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.NetworkCallback;

import java.util.List;

public class HomeFragPresenter implements NetworkCallback {

    private HomeFragView _view;
    private Repo _repo;

    public HomeFragPresenter(HomeFragView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public void getRandomMeal() {
        _repo.getRandomMeal(this);
    }

    public void getCategories() {
        _repo.getCategories(this);
    }

    public void getCountries() {
        Log.i(TAG, "check in home fragment presenter country fetch");
        _repo.getCountries(this);
    }

    public void getMealsByCategory(String category) {
        _repo.getMealsByCategory(category, this);
    }

    public void getIngridents(){
        Log.d(TAG, "Fetching ingredients from repository");
        _repo.getIngredints(this);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            _view.showData(meals);
        } else {
            _view.showErrMsg("No meals found for this category.");
        }
    }

    @Override
    public void onSuccessResultCate(List<Category> categories) {
        _view.showDataCate(categories);
    }

    @Override
    public void onSuccessResultCountry(List<Country> countries) {
        _view.showDataCountries(countries);
    }

    @Override
    public void onSuccessResultIngridents(List<Ingridents> ingridents) {
        Log.d(TAG, "Fetched ingredients, size: " + (ingridents != null ? ingridents.size() : 0));
        _view.showDataIngridents(ingridents);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }
}

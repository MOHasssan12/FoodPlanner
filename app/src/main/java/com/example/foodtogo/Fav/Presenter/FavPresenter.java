package com.example.foodtogo.Fav.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.Fav.View.FavView;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.NetworkCallback;

import java.util.List;

public class FavPresenter  {

    private FavView _view;
    private Repo _repo;

    public FavPresenter(FavView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public LiveData<List<Meal>> getAllFavMeals(){
       return  _repo.getAllFavMeals();
    }

    public void deleteFavMeal(Meal meal){
        _repo.deleteMeal(meal);
    }
    public void insertMeal(Meal meal){
        _repo.insertMeal(meal);
    }

    public LiveData<Meal> getMeal(String mealName){
      return  _repo.getMeal(mealName);
    }



}

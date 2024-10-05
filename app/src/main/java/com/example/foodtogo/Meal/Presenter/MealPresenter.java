package com.example.foodtogo.Meal.Presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.Meal.View.MealView;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.NetworkCallback;

import java.util.Arrays;
import java.util.List;

public class MealPresenter implements NetworkCallback {

    private MealView _view;
    private Repo _repo;

    public MealPresenter(MealView _view, Repo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public void insertMeal(Meal meal){
        _repo.insertMeal(meal);
    }

    public void getMealDetails(String mealName){
        _repo.getMealByName(mealName,this);

    }

    public void addMealsToPlan(Meal meal,String date){
        _repo.addMealToPlan(meal,date);
    }

    public void getMeal(String mealName){
        LiveData<Meal> meal = _repo.getMeal(mealName);
        if (meal != null) {
            _view.showDataForFavMeal(meal);
        } else {
            _view.showErrMsg("Meal not found in favorites");
        }
    }

    public List<String> getIngredients(Meal meal) {
        StringBuilder sb = new StringBuilder();

        appendIfNotNullOrEmpty(sb, meal.getStrIngredient1());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient2());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient3());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient4());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient5());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient6());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient7());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient8());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient9());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient10());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient11());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient12());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient13());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient14());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient15());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient16());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient17());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient18());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient19());
        appendIfNotNullOrEmpty(sb, meal.getStrIngredient20());

        return Arrays.asList(sb.toString().split(","));
    }


    public List<String> getMeasures(Meal meal) {
        StringBuilder sb = new StringBuilder();

        appendIfNotNullOrEmpty(sb, meal.getStrMeasure1());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure2());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure3());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure4());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure5());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure6());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure7());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure8());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure9());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure10());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure11());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure12());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure13());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure14());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure15());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure16());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure17());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure18());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure19());
        appendIfNotNullOrEmpty(sb, meal.getStrMeasure20());

        return Arrays.asList(sb.toString().split(","));
    }

    private void appendIfNotNullOrEmpty(StringBuilder sb, String str) {
        if (str != null && !str.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(str);
        }
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

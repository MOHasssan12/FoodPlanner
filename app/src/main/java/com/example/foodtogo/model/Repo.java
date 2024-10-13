package com.example.foodtogo.model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.network.MealsRemoteDataSource;
import com.example.foodtogo.network.NetworkCallback;

import java.util.List;

public class Repo {


    MealsRemoteDataSource remoteDataSource;
    MealsLocalDataSource localDataSource;



    private static Repo repo = null;
    public static Repo getInstance(MealsRemoteDataSource remoteDataSource,MealsLocalDataSource localDataSource){
        if(repo == null){
            repo = new Repo(remoteDataSource,localDataSource);
        }
        return repo;
    }

    private Repo(MealsRemoteDataSource remoteDataSource,MealsLocalDataSource localDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    //Retrofit Methods

    public void getRandomMeal(NetworkCallback networkCallback){
        remoteDataSource.getRandomMeal(networkCallback);
    }

    public void getCategories(NetworkCallback networkCallback){
        remoteDataSource.listCategories(networkCallback);
    }

    public void getCountries(NetworkCallback networkCallback){
        Log.i(TAG,"check fetching countries");
        remoteDataSource.listCountries(networkCallback);
    }

    public void getMealsByCategory(String category,NetworkCallback networkCallback){
        remoteDataSource.getMealsByCategory(category,networkCallback);
    }

   public void  getMealsByCountries(String country,NetworkCallback networkCallback){
        remoteDataSource.getMealsByCountries(country,networkCallback);
    }

    public void getMealsByIngridents(String ingrident,NetworkCallback networkCallback){
        remoteDataSource.getMealsByIngridents(ingrident,networkCallback);
    }


    public void getIngredints(NetworkCallback networkCallback){
        remoteDataSource.listIngridents(networkCallback);
    }


    public void getMealByName(String mealName , NetworkCallback networkCallback){
        remoteDataSource.getMealByName(mealName,networkCallback);
    }



    // DataBase Methods

    public void insertMeal(Meal meal){
        localDataSource.insertMeal(meal);
    }

    public void addMealToFav(Meal meal){
        localDataSource.addMealToFav(meal);
    }


    public LiveData<List<Meal>> getAllFavMeals(){
       return  localDataSource.getAllFavMeals();
    }

    public LiveData<Meal> getMeal(String mealName){
        return  localDataSource.getMeal(mealName);
    }


    public void addMealToPlan(Meal meal , String date){
        localDataSource.addMealToPlan(meal,date);
    }


    public void deleteMeal(Meal meal){
        localDataSource.deleteMeal(meal);
    }

    public LiveData<List<Meal>> getMealsForDate(String date){
        return localDataSource.getMealsForDate(date);
    }


}

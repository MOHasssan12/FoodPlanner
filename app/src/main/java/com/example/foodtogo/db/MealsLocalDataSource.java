package com.example.foodtogo.db;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodtogo.Home.View.HomeFragment;
import com.example.foodtogo.model.Meal;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealsLocalDataSource {

    private MealDAO dao ;
    private static MealsLocalDataSource localDataSource = null ;
    private LiveData<List<Meal>> favMeals;


    private MealsLocalDataSource(Context context){
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        dao = db.getMealDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                favMeals = dao.getPojos();
            }
        }).start();

    }

    public static MealsLocalDataSource getInstance(Context context) {
        if(localDataSource == null){
            localDataSource = new MealsLocalDataSource(context);
        }
        return localDataSource;
    }

    public void insertMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMeal(meal);
            }
        }).start();
    }
    public void deleteMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMeal(meal);
            }
        }).start();
    }

    public void addMealToPlan(Meal meal, String date) {
        meal.isPlanned = true;
        meal.planDate = date;
        new Thread(() -> dao.insertMeal(meal)).start();
    }

    public LiveData<List<Meal>> getMealsForDate(String date){
        return dao.getMealsForDate(date);
    }


    public LiveData<List<Meal>> getAllFavMeals(){
        return favMeals;
    }

    public LiveData<Meal> getMeal(String mealName) {
        return dao.getMealByName(mealName);
    }

}


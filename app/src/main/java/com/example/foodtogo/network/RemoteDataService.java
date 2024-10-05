package com.example.foodtogo.network;

import com.example.foodtogo.model.CategoryResponse;
import com.example.foodtogo.model.CountryResponse;
import com.example.foodtogo.model.IngredientResponse;
import com.example.foodtogo.model.MealResponse;
import com.example.foodtogo.model.Repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteDataService {

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoryResponse> listCategories();


    @GET("list.php?a=list")
    Call<CountryResponse> listCountries();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("search.php")
    Call<MealResponse> getMealByName(@Query("s") String mealName);

    @GET("list.php?i=list")
    Call<IngredientResponse> listIngridents();

    @GET("filter.php")
    Call<MealResponse> getMealsByCountries(@Query("a") String country);

    @GET("filter.php")
    Call<MealResponse> getMealsByIngridents(@Query("i") String ingridnt);

}

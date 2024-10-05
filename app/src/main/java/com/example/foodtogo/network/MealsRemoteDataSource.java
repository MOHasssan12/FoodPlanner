package com.example.foodtogo.network;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodtogo.model.CategoryResponse;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.CountryResponse;
import com.example.foodtogo.model.IngredientResponse;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.MealResponse;
import com.example.foodtogo.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealsRemoteDataSource client = null;
    private RemoteDataService remoteDataService;

    private MealsRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();
        remoteDataService = retrofit.create(RemoteDataService.class);
    }

    public static MealsRemoteDataSource getInstance() {
        if (client == null) {
            client = new MealsRemoteDataSource();
        }
        return client;
    }


    public void getRandomMeal(NetworkCallback networkCallback) {
        Call<MealResponse> call = remoteDataService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallback.onSuccessResult(response.body().getMeals());
                } else {
                    networkCallback.onFailureResult("Meal not found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }
    public void getMealByName(String mealName, NetworkCallback networkCallback) {
        Log.d("MealActivity", "Making network request for meal: " + mealName);
        if (mealName == null || mealName.isEmpty()) {
            networkCallback.onFailureResult("Meal name cannot be null or empty");
            return;
        }
        Call<MealResponse> call = remoteDataService.getMealByName(mealName);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        networkCallback.onSuccessResult(meals);
                        Log.d("MealActivity", "Received meals: " + meals);
                    } else {
                        networkCallback.onFailureResult("No meals found for the given name");
                    }
                } else {
                    Log.d("MealActivity", "Response failed with code: " + response.code());
                    networkCallback.onFailureResult("Meal not found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.e("MealActivity", "Network error: " + throwable.getMessage());
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }


    public void listCategories(NetworkCallback networkCallbackCat) {
        Call<CategoryResponse> call = remoteDataService.listCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body().toString());
                    networkCallbackCat.onSuccessResultCate(response.body().getCategories());
                    Log.i(TAG, "categories fetched ");
                } else {
                    networkCallbackCat.onFailureResult("No categories found");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                networkCallbackCat.onFailureResult(throwable.getMessage());
            }
        });
    }




    public void getMealsByIngridents(String ingrident, NetworkCallback networkCallback) {
        Call<MealResponse> call = remoteDataService.getMealsByIngridents(ingrident);
        call.enqueue(new Callback<MealResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        Meal firstMeal = meals.get(0);
                        Log.d(TAG, "First Meal: " + firstMeal.toString());
                        networkCallback.onSuccessResult(meals);
                        Log.i(TAG, "Meals fetched successfully");
                    } else {
                        networkCallback.onFailureResult("No meals found");
                    }
                } else {
                    networkCallback.onFailureResult("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });

    }





    public void getMealsByCountries(String country, NetworkCallback networkCallback) {
        Call<MealResponse> call = remoteDataService.getMealsByCountries(country);
        call.enqueue(new Callback<MealResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        Meal firstMeal = meals.get(0);
                        Log.d(TAG, "First Meal: " + firstMeal.toString());
                        networkCallback.onSuccessResult(meals);
                        Log.i(TAG, "Meals fetched successfully");
                    } else {
                        networkCallback.onFailureResult("No meals found");
                    }
                } else {
                    networkCallback.onFailureResult("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });

    }



    public void getMealsByCategory(String category, NetworkCallback networkCallback) {
        Call<MealResponse> call = remoteDataService.getMealsByCategory(category);
        call.enqueue(new Callback<MealResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        Meal firstMeal = meals.get(0);
                        Log.d(TAG, "First Meal: " + firstMeal.toString());
                        networkCallback.onSuccessResult(meals);
                        Log.i(TAG, "Meals fetched successfully");
                    } else {
                        networkCallback.onFailureResult("No meals found");
                    }
                } else {
                    networkCallback.onFailureResult("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });

    }


    public void listCountries(NetworkCallback networkCallback) {
        Call<CountryResponse> call = remoteDataService.listCountries();
        call.enqueue(new Callback<CountryResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> countries = response.body().getCountries();

                    if (countries != null && !countries.isEmpty()) {
                        Log.d(TAG, "First Country: " + countries.get(0).toString());
                    }

                    Log.d(TAG, "Response: " + response.body().toString());
                    networkCallback.onSuccessResultCountry(countries);
                    Log.i(TAG, "on countries fetched ");
                } else {
                    networkCallback.onFailureResult("No countries found");
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

    public void listIngridents(NetworkCallback networkCallbackCat) {
        Call<IngredientResponse> call = remoteDataService.listIngridents();
        call.enqueue(new Callback<IngredientResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body().toString());
                    networkCallbackCat.onSuccessResultIngridents(response.body().getIngredients());
                    Log.i(TAG, "Ingridents fetched ");
                } else {
                    networkCallbackCat.onFailureResult("No categories found");
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable throwable) {
                networkCallbackCat.onFailureResult(throwable.getMessage());
            }
        });
    }


}

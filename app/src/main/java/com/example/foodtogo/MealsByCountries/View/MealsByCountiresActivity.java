package com.example.foodtogo.MealsByCountries.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.MealsByCategories.Presenter.CategoriesMealsPresenter;
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsAdapter;
import com.example.foodtogo.MealsByCountries.Presenter.MealsbyCountriesPresenter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class MealsByCountiresActivity extends AppCompatActivity implements MealsbyCountriesView {


    private MealsbyCountriesPresenter presenter;
    private MealsRemoteDataSource mealClient;
    private MealsLocalDataSource localDataSource;
    private RecyclerView recyclerView;
    private MealsByCountiresAdapter adapter;
    private TextView txt_Country_name;
    private  String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals_by_countires);


        countryName = getIntent().getStringExtra("CountryName");


        presenter = new MealsbyCountriesPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(this)));
        presenter.getMeals(countryName);

        txt_Country_name =findViewById(R.id.txt_country_name);

        recyclerView = findViewById(R.id.rv_meals);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MealsByCountiresAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mealsbycountries), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            txt_Country_name.setText("Popular Meals In "+countryName);
            adapter.setList(meals);
            adapter.notifyDataSetChanged();
            Log.d(TAG, " meals available" + meals.toString());
        } else {
            Log.d(TAG, "No meals available");
        }

    }

    @Override
    public void showErrMsg(String error) {

    }
}
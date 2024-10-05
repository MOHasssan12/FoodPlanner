package com.example.foodtogo.MealsByCategories.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.Home.Presenter.HomeFragPresenter;
import com.example.foodtogo.Home.View.CategoriesAdapter;
import com.example.foodtogo.Home.View.CountriesAdapter;
import com.example.foodtogo.MealsByCategories.Presenter.CategoriesMealsPresenter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMealsActivity extends AppCompatActivity implements CategoriesMealsView {



    private CategoriesMealsPresenter presenter;
    private MealsRemoteDataSource mealClient;
    private MealsLocalDataSource localDataSource;
    private RecyclerView recyclerView;
    private CategoriesMealsAdapter adapter;
    private TextView txt_category_name;
    private  String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories_meals);


         categoryName = getIntent().getStringExtra("CATEGORY_NAME");


        presenter = new CategoriesMealsPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(this)));
        presenter.getMeals(categoryName);

        txt_category_name=findViewById(R.id.txt_category_name);

        recyclerView = findViewById(R.id.rv_meals);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CategoriesMealsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mealsbycategories2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            txt_category_name.setText(categoryName+" Category ");
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
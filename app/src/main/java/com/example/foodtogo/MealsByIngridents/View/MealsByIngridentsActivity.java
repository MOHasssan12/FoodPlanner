package com.example.foodtogo.MealsByIngridents.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.MealsByCountries.Presenter.MealsbyCountriesPresenter;
import com.example.foodtogo.MealsByCountries.View.MealsByCountiresAdapter;
import com.example.foodtogo.MealsByIngridents.Presenter.MealsByIngridentsPresenter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class MealsByIngridentsActivity extends AppCompatActivity implements MealsByIngridentsView {


    private MealsByIngridentsPresenter presenter;
    private MealsRemoteDataSource mealClient;
    private MealsLocalDataSource localDataSource;
    private RecyclerView recyclerView;
    private MealsByIngridentsAdapter adapter;
    private TextView txt_ingrident_name;
    private  String ingridentName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals_by_ingridents);


        ingridentName = getIntent().getStringExtra("IngridentName");


        presenter = new MealsByIngridentsPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(this)));
        presenter.getMeals(ingridentName);

        txt_ingrident_name =findViewById(R.id.txt_ingrident_name);

        recyclerView = findViewById(R.id.rv_meals);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MealsByIngridentsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mealsbyingridents), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            txt_ingrident_name.setText("All Meals With "+ingridentName);
            adapter.setList(meals);
            adapter.notifyDataSetChanged();
            Log.d(TAG, " meals available" + meals.toString());
        } else {
            Toast.makeText(this, "There is no Meals Available For This Ingrident Please Go Back ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void showErrMsg(String error) {

    }

}


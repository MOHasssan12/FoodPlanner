package com.example.foodtogo.Meal.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtogo.Meal.Presenter.MealPresenter;
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsAdapter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.AppDataBase;
import com.example.foodtogo.db.MealDAO;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MealActivity extends AppCompatActivity implements MealView {

    private AppBarLayout appBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton favButton;
    private FloatingActionButton addButton;
    private TextView txtIngredients;
    private RecyclerView rvIngredients;
    private TextView txtInstruction;
    private TextView blankText;
    private WebView video;
    private ImageView imgMealDetails;
    private TextView txtCategory;
    private TextView txtCountry;
    private MealPresenter presenter;
    MealDAO mealDAO;
    IngidentsAdapter adapter;

    private List<String> ingridents;
    private List<String> measures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal);

        appBar = findViewById(R.id.appbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        favButton = findViewById(R.id.fav_button);
        addButton = findViewById(R.id.add_button);
        txtIngredients = findViewById(R.id.txtingridents);
        txtInstruction = findViewById(R.id.txtinstruction);
        blankText = findViewById(R.id.blanktxt);
        video = findViewById(R.id.video);
        imgMealDetails = findViewById(R.id.img_meal_details);
        txtCategory = findViewById(R.id.txtcategory);
        txtCountry = findViewById(R.id.txtCountry);

        AppDataBase appDataBase = AppDataBase.getInstance(this);
        mealDAO =appDataBase.getMealDAO();


        presenter = new MealPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(this)));

        Intent intent = getIntent();
        String mealName = intent.getStringExtra("MEAL_NAME");
        String source = intent.getStringExtra("SOURCE");

        if (source != null && source.equals("FavAdapter")) {
            presenter.getMeal(mealName);
        } else {
            presenter.getMealDetails(mealName);
        }

        rvIngredients = findViewById(R.id.rv_ingridents);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new IngidentsAdapter(this , new ArrayList<>(),new ArrayList<>(),presenter);
        rvIngredients.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mealsbycategories), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void MealDetails(Meal meal) {
        collapsingToolbar.setTitle(meal.getStrMeal());
        txtCountry.setText( meal.getStrArea());
        txtCategory.setText(meal.getStrCategory());
        video.getSettings().setJavaScriptEnabled(true);
        video.setWebViewClient(new WebViewClient());
        String youtubeUrl = meal.getStrYoutube();
        video.loadUrl(youtubeUrl);
        blankText.setText(meal.getStrInstructions());
        Glide.with(this).load(meal.getStrMealThumb()).into(imgMealDetails);
    }






    public void showDataForFavMeal(LiveData<Meal> mealLiveData) {
        mealLiveData.observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                if (meal != null) {
                    List<String> measures = presenter.getMeasures(meal);
                    List<String> ingredients = presenter.getIngredients(meal);
                    adapter.setList(measures, ingredients);
                    adapter.notifyDataSetChanged();
                    MealDetails(meal);
                } else {
                    imgMealDetails.setImageResource(R.drawable.ic_launcher_background);
                    Toast.makeText(MealActivity.this, "No meal details available in favorites", Toast.LENGTH_SHORT).show();
                }

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();

                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                MealActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        String selectedDate = dateFormat.format(calendar.getTime());
                                        presenter.addMealsToPlan(meal, selectedDate);
                                        Toast.makeText(MealActivity.this, "Meal added to your plan", Toast.LENGTH_SHORT).show();
                                    }
                                },

                                year, month, day);

                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());


                        datePickerDialog.show();
                    }
                });
                favButton.setOnClickListener(view -> {
                    Toast.makeText(MealActivity.this, "Meal Is Already In The Favorites", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);
            measures=presenter.getMeasures(meal);
            ingridents= presenter.getIngredients(meal);
            adapter.setList(measures,ingridents);
            adapter.notifyDataSetChanged();
            MealDetails(meal);
        } else {
            imgMealDetails.setImageResource(R.drawable.ic_launcher_background);
            Toast.makeText(this, "No meal details available", Toast.LENGTH_SHORT).show();
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MealActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String selectedDate = dateFormat.format(calendar.getTime());
                                presenter.addMealsToPlan(meals.get(0), selectedDate);
                                Toast.makeText(MealActivity.this, "meal added to your plan ", Toast.LENGTH_SHORT).show();


                            }
                        },

                        year, month, day);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());




                datePickerDialog.show();
            }
        });

        favButton.setOnClickListener(view -> {
               presenter.insertMeal(meals.get(0));
            Toast.makeText(MealActivity.this, "Meal is added to favorites", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void showErrMsg(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}


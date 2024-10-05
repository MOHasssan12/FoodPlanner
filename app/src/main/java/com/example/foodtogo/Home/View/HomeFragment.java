package com.example.foodtogo.Home.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodtogo.Home.Presenter.HomeFragPresenter;
import com.example.foodtogo.Meal.View.MealActivity;
import com.example.foodtogo.R;
import com.example.foodtogo.Search.View.SearchFragment;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragView {

    private ImageView insirMeal;
    private ImageView search;
    private TextView txt_meal_name;
    private HomeFragPresenter presenter;
    private MealsRemoteDataSource mealClient;
    private MealsLocalDataSource localDataSource;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCountry;
    private RecyclerView recyclerViewIngr;
    private CategoriesAdapter adapter;
    private CountriesAdapter adaptercountry;
    private IngridentsAdapter adapterIng;
    private  String mealname;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new HomeFragPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext())));
        presenter.getRandomMeal();

        search = view.findViewById(R.id.img_sreach);
        insirMeal = view.findViewById(R.id.img_random_meal);
        txt_meal_name = view.findViewById(R.id.txt_meal_name);

        recyclerView = view.findViewById(R.id.rec_view_categories);
        recyclerView.setHasFixedSize(true);

        recyclerViewCountry = view.findViewById(R.id.rec_view_countries);
        recyclerViewCountry.setHasFixedSize(true);

        recyclerViewIngr = view.findViewById(R.id.rec_view_ingridents);
        recyclerViewIngr.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManagerIngr = new GridLayoutManager(getContext(), 4);
        gridLayoutManagerIngr.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewIngr.setLayoutManager(gridLayoutManagerIngr);

        LinearLayoutManager linearLayoutManagerCountry = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCountry.setLayoutManager(linearLayoutManagerCountry);

        adaptercountry=new CountriesAdapter(getContext(),new ArrayList<>());
        recyclerViewCountry.setAdapter(adaptercountry);
        presenter.getCountries();

        adapter = new CategoriesAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        presenter.getCategories();

        adapterIng = new IngridentsAdapter(getContext(), new ArrayList<>());
        recyclerViewIngr.setAdapter(adapterIng);
        presenter.getIngridents();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_homeFragment_to_searchFragment);
            }
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        if (getActivity() == null || !isAdded()) return;
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);
            mealname=meal.getStrMeal();
            txt_meal_name.setText(mealname);
            Glide.with(this).load(meal.getStrMealThumb()).into(insirMeal);
        } else {
            insirMeal.setImageResource(R.drawable.ic_launcher_background);
        }

        insirMeal.setOnClickListener(view -> {
            Context context = getContext();
            if (context != null) {
                Intent intent = new Intent(context, MealActivity.class);
                String mealName = mealname;
                intent.putExtra("MEAL_NAME", mealName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void showDataCate(List<Category> categories) {
        adapter.setList(categories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDataIngridents(List<Ingridents> ingridents) {
        Log.d(TAG, "Displaying ingredients, size: " + (ingridents != null ? ingridents.size() : 0));
        adapterIng.setList(ingridents);
        adapterIng.notifyDataSetChanged();
    }

    @Override
    public void showDataCountries(List<Country> countries) {
        if (countries == null) {
            Log.i(TAG, "countries is empty");
        } else {
            adaptercountry.setList(countries);
            adaptercountry.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrMsg(String error) {

    }
}

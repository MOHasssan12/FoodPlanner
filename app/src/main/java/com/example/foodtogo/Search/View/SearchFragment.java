package com.example.foodtogo.Search.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtogo.Home.Presenter.HomeFragPresenter;
import com.example.foodtogo.Home.View.CategoriesAdapter;
import com.example.foodtogo.Home.View.CountriesAdapter;
import com.example.foodtogo.Home.View.IngridentsAdapter;
import com.example.foodtogo.R;
import com.example.foodtogo.Search.Presenter.SearchFragmentPresenter;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;
import androidx.appcompat.widget.SearchView;


import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchFragmentView {

    private SearchView searchView;
    private SearchFragmentPresenter presenter;
    private MealsRemoteDataSource mealClient;
    private MealsLocalDataSource localDataSource;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new SearchFragmentPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext())));


        recyclerView = view.findViewById(R.id.rv_meals);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SearchAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getMeals(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.setList(new ArrayList<>());
                }
                return false;
            }
        });
    }


    @Override
    public void showData(List<Meal> meal) {
        adapter.setList(meal);

    }

    @Override
    public void showErrMsg(String error) {

    }
}



package com.example.foodtogo.Fav.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtogo.Fav.Presenter.FavPresenter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements FavView {

    private FavPresenter presenter;
    private RecyclerView recyclerView;
    private FavAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getContext(), "To Delete a Meal Swipe Left or Right", Toast.LENGTH_SHORT).show();

        presenter = new FavPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext())));

        recyclerView = view.findViewById(R.id.rv_fav);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new FavAdapter(getContext(), new ArrayList<>(),presenter);
        recyclerView.setAdapter(adapter);

        presenter.getAllFavMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                adapter.setList(meals);
                adapter.notifyDataSetChanged();
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Meal deletedMeal = adapter.getMealAtPosition(position);
                presenter.deleteFavMeal(deletedMeal);
                adapter.removeMealAtPosition(position);

                // Notify adapter of the item removed and range changed
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());

                Snackbar snackbar = Snackbar.make(recyclerView, "Meal deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", v -> {
                    presenter.insertMeal(deletedMeal);
                    adapter.addMealAtPosition(deletedMeal);
                    adapter.notifyDataSetChanged();
                });
                snackbar.show();
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void showData(List<Meal> meal) {
        adapter.setList(meal);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {
    }
}

package com.example.foodtogo.planner.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodtogo.Home.Presenter.HomeFragPresenter;
import com.example.foodtogo.Home.View.CategoriesAdapter;
import com.example.foodtogo.R;
import com.example.foodtogo.db.MealsLocalDataSource;
import com.example.foodtogo.model.Meal;
import com.example.foodtogo.model.Repo;
import com.example.foodtogo.network.MealsRemoteDataSource;
import com.example.foodtogo.planner.Presenter.PlannerPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.Arrays;

public class PlannerFragment extends Fragment implements PlannerView {

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private PlannerAdapter adapter;
    private PlannerPresenter presenter;
    private TextView dayText1, dayText2, dayText3, dayText4, dayText5, dayText6, dayText7;
    private TextView textView_day_name, textView_date;
    public int satMonth;
    public int sunMonth;
    public int monMonth;
    public int tueMonth;
    public int wedMonth;
    public int thuMonth;
    public int friMonth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_day_name = view.findViewById(R.id.textView_day_name);
        textView_date = view.findViewById(R.id.textView_date);
        dayText1 = view.findViewById(R.id.day_text_1);
        dayText2 = view.findViewById(R.id.day_text_2);
        dayText3 = view.findViewById(R.id.day_text_3);
        dayText4 = view.findViewById(R.id.day_text_4);
        dayText5 = view.findViewById(R.id.day_text_5);
        dayText6 = view.findViewById(R.id.day_text_6);
        dayText7 = view.findViewById(R.id.day_text_7);
        addButton = view.findViewById(R.id.add_meal_fab);



                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_plannerFragment_to_searchFragment);
                    }
                });


        recyclerView = view.findViewById(R.id.meal_plan_recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerIngr = new GridLayoutManager(getContext(), 2);
        gridLayoutManagerIngr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManagerIngr);

        adapter = new PlannerAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        presenter = new PlannerPresenter(this, Repo.getInstance(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext())));





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

                Snackbar snackbar = Snackbar.make(recyclerView, "Meal deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", v -> {

                    presenter.insertMeal(deletedMeal);
                    adapter.notifyItemInserted(position);
                });
                snackbar.show();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);



        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String currentDate = dateFormat.format(calendar.getTime());
        presenter.getMealsForDate(currentDate);


        switch (today) {
            case Calendar.SATURDAY:
                satMonth = dayOfMonth;
                sunMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                monMonth = (dayOfMonth + 2 > maxDaysInMonth) ? (dayOfMonth + 2 - maxDaysInMonth) : dayOfMonth + 2;
                tueMonth = (dayOfMonth + 3 > maxDaysInMonth) ? (dayOfMonth + 3 - maxDaysInMonth) : dayOfMonth + 3;
                wedMonth = (dayOfMonth + 4 > maxDaysInMonth) ? (dayOfMonth + 4 - maxDaysInMonth) : dayOfMonth + 4;
                thuMonth = (dayOfMonth + 5 > maxDaysInMonth) ? (dayOfMonth + 5 - maxDaysInMonth) : dayOfMonth + 5;
                friMonth = (dayOfMonth + 6 > maxDaysInMonth) ? (dayOfMonth + 6 - maxDaysInMonth) : dayOfMonth + 6;
                textView_day_name.setText("Saturday");
                textView_date.setText(String.valueOf(satMonth));
                break;
            case Calendar.SUNDAY:
                satMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                sunMonth = dayOfMonth;
                monMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                tueMonth = (dayOfMonth + 2 > maxDaysInMonth) ? (dayOfMonth + 2 - maxDaysInMonth) : dayOfMonth + 2;
                wedMonth = (dayOfMonth + 3 > maxDaysInMonth) ? (dayOfMonth + 3 - maxDaysInMonth) : dayOfMonth + 3;
                thuMonth = (dayOfMonth + 4 > maxDaysInMonth) ? (dayOfMonth + 4 - maxDaysInMonth) : dayOfMonth + 4;
                friMonth = (dayOfMonth + 5 > maxDaysInMonth) ? (dayOfMonth + 5 - maxDaysInMonth) : dayOfMonth + 5;
                textView_day_name.setText("Sunday");
                textView_date.setText(String.valueOf(sunMonth));
                break;
            case Calendar.MONDAY:
                satMonth = (dayOfMonth - 2 < 1) ? maxDaysInMonth + (dayOfMonth - 2) : dayOfMonth - 2;
                sunMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                monMonth = dayOfMonth;
                tueMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                wedMonth = (dayOfMonth + 2 > maxDaysInMonth) ? (dayOfMonth + 2 - maxDaysInMonth) : dayOfMonth + 2;
                thuMonth = (dayOfMonth + 3 > maxDaysInMonth) ? (dayOfMonth + 3 - maxDaysInMonth) : dayOfMonth + 3;
                friMonth = (dayOfMonth + 4 > maxDaysInMonth) ? (dayOfMonth + 4 - maxDaysInMonth) : dayOfMonth + 4;
                textView_day_name.setText("Monday");
                textView_date.setText(String.valueOf(monMonth));
                break;
            case Calendar.TUESDAY:
                satMonth = (dayOfMonth - 3 < 1) ? maxDaysInMonth + (dayOfMonth - 3) : dayOfMonth - 3;
                sunMonth = (dayOfMonth - 2 < 1) ? maxDaysInMonth + (dayOfMonth - 2) : dayOfMonth - 2;
                monMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                tueMonth = dayOfMonth;
                wedMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                thuMonth = (dayOfMonth + 2 > maxDaysInMonth) ? (dayOfMonth + 2 - maxDaysInMonth) : dayOfMonth + 2;
                friMonth = (dayOfMonth + 3 > maxDaysInMonth) ? (dayOfMonth + 3 - maxDaysInMonth) : dayOfMonth + 3;
                textView_day_name.setText("Tuesday");
                textView_date.setText(String.valueOf(tueMonth));
                break;
            case Calendar.WEDNESDAY:
                satMonth = (dayOfMonth - 4 < 1) ? maxDaysInMonth + (dayOfMonth - 4) : dayOfMonth - 4;
                sunMonth = (dayOfMonth - 3 < 1) ? maxDaysInMonth + (dayOfMonth - 3) : dayOfMonth - 3;
                monMonth = (dayOfMonth - 2 < 1) ? maxDaysInMonth + (dayOfMonth - 2) : dayOfMonth - 2;
                tueMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                wedMonth = dayOfMonth;
                thuMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                friMonth = (dayOfMonth + 2 > maxDaysInMonth) ? (dayOfMonth + 2 - maxDaysInMonth) : dayOfMonth + 2;
                textView_day_name.setText("Wednesday");
                textView_date.setText(String.valueOf(wedMonth));
                break;
            case Calendar.THURSDAY:
                satMonth = (dayOfMonth - 5 < 1) ? maxDaysInMonth + (dayOfMonth - 5) : dayOfMonth - 5;
                sunMonth = (dayOfMonth - 4 < 1) ? maxDaysInMonth + (dayOfMonth - 4) : dayOfMonth - 4;
                monMonth = (dayOfMonth - 3 < 1) ? maxDaysInMonth + (dayOfMonth - 3) : dayOfMonth - 3;
                tueMonth = (dayOfMonth - 2 < 1) ? maxDaysInMonth + (dayOfMonth - 2) : dayOfMonth - 2;
                wedMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                thuMonth = dayOfMonth;
                friMonth = (dayOfMonth + 1 > maxDaysInMonth) ? 1 : dayOfMonth + 1;
                textView_day_name.setText("Thursday");
                textView_date.setText(String.valueOf(thuMonth));
                break;
            case Calendar.FRIDAY:
                satMonth = (dayOfMonth - 6 < 1) ? maxDaysInMonth + (dayOfMonth - 6) : dayOfMonth - 6;
                sunMonth = (dayOfMonth - 5 < 1) ? maxDaysInMonth + (dayOfMonth - 5) : dayOfMonth - 5;
                monMonth = (dayOfMonth - 4 < 1) ? maxDaysInMonth + (dayOfMonth - 4) : dayOfMonth - 4;
                tueMonth = (dayOfMonth - 3 < 1) ? maxDaysInMonth + (dayOfMonth - 3) : dayOfMonth - 3;
                wedMonth = (dayOfMonth - 2 < 1) ? maxDaysInMonth + (dayOfMonth - 2) : dayOfMonth - 2;
                thuMonth = (dayOfMonth - 1 < 1) ? maxDaysInMonth : dayOfMonth - 1;
                friMonth = dayOfMonth;
                textView_day_name.setText("Friday");
                textView_date.setText(String.valueOf(friMonth));
                break;
            default:
                satMonth = sunMonth = monMonth = tueMonth = wedMonth = thuMonth = friMonth = dayOfMonth;
                textView_day_name.setText("Unknown");
                textView_date.setText(String.valueOf(dayOfMonth));
                break;
        }



        dayText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Saturday");
                textView_date.setText(String.valueOf(satMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, satMonth - calendar.get(Calendar.DAY_OF_MONTH)); // Adjust the day
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);

            }
        });
        dayText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Sunday");
                textView_date.setText(String.valueOf(sunMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, sunMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
        dayText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Monday");
                textView_date.setText(String.valueOf(monMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, monMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
        dayText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Tuesday");
                textView_date.setText(String.valueOf(tueMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, tueMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
        dayText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Wednesday");
                textView_date.setText(String.valueOf(wedMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, wedMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
        dayText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Thursday");
                textView_date.setText(String.valueOf(thuMonth)+" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, thuMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
        dayText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_day_name.setText("Friday");
                textView_date.setText(String.valueOf(friMonth) +" Day In October " );
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, friMonth - calendar.get(Calendar.DAY_OF_MONTH));
                String selectedDate = dateFormat.format(calendar.getTime());
                presenter.getMealsForDate(selectedDate);
            }
        });
    }

    @Override
    public void showData(LiveData<List<Meal>> mealsLiveData) {
        mealsLiveData.observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                adapter.setList(meals);

            }
        });
    }
    @Override
    public void showErrMsg(String error) {
    }
}

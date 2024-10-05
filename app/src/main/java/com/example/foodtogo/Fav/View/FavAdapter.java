package com.example.foodtogo.Fav.View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtogo.Fav.Presenter.FavPresenter;
import com.example.foodtogo.Home.View.CategoriesAdapter;
import com.example.foodtogo.Meal.View.MealActivity;
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsAdapter;
import com.example.foodtogo.R;
import com.example.foodtogo.model.Meal;

import java.util.List;

public class FavAdapter  extends RecyclerView.Adapter<FavAdapter.ViewHolder> {


    private final Context context;
    private List<Meal> values;
    private static final String TAG = "RecyclerView";
    private FavPresenter presenter;

    public FavAdapter(Context context, List<Meal> values,FavPresenter presenter) {
        this.context = context;
        this.values = values;
        this.presenter=presenter;
    }


    public void setList(List<Meal> values) {
        this.values = values;
        notifyDataSetChanged();
    }
    public Meal getMealAtPosition(int position) {
        return values.get(position);
    }

    public void removeMealAtPosition(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView txtMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.img_meal);
            txtMealName = itemView.findViewById(R.id.txt_meal_name);
        }
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.meal_item, parent, false);
        return new FavAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "Binding view for position: " + position);
        Meal meal = values.get(position);
        holder.txtMealName.setText(meal.getStrMeal());

        Glide.with(context).load(meal.getStrMealThumb()).into(holder.imgMeal);

        holder.imgMeal.setOnClickListener(view -> {
            Intent intent = new Intent(context, MealActivity.class);
            String mealName = meal.getStrMeal();
            intent.putExtra("MEAL_NAME", mealName);
            intent.putExtra("SOURCE", "FavAdapter");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (values != null) ? values.size() : 0;
    }
}

package com.example.foodtogo.Meal.View;

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
import com.example.foodtogo.Home.Presenter.HomeFragPresenter;
import com.example.foodtogo.Home.View.CategoriesAdapter;
import com.example.foodtogo.Meal.Presenter.MealPresenter;
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsActivity;
import com.example.foodtogo.R;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Ingridents;
import com.example.foodtogo.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class IngidentsAdapter extends RecyclerView.Adapter<IngidentsAdapter.ViewHolder> {


    private List<String> ingridents;
    private List<String> measures;
    private final Context context;
    private List<Meal> values;
    private MealPresenter presenter;
    private static final String TAG = "RecyclerView";

    public IngidentsAdapter(Context context, List<String> measures, List<String> ingridents, MealPresenter presenter) {
        this.context = context;
        this.measures = measures;
        this.ingridents = ingridents;
        this.presenter = presenter;
    }

    public void setList(List<String> measures, List<String> ingridents) {
      this.ingridents = ingridents;
      this.measures = measures;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIngridents;
        TextView txtIngridentsName;
        TextView txtMeasure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIngridents = itemView.findViewById(R.id.img_ingrident);
            txtIngridentsName = itemView.findViewById(R.id.txt_ingrident_name);
            txtMeasure = itemView.findViewById(R.id.txt_measure);
        }
    }

    @NonNull
    @Override
    public IngidentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.ingridents_in_meal_details, parent, false);
        return new IngidentsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String ingredientName = ingridents.get(position);
        String measureName = measures.get(position);
        String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientName +".png";

        holder.txtIngridentsName.setText(ingredientName);
        holder.txtMeasure.setText(measureName);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .override(150, 150)
                .into(holder.imgIngridents);



    }

//    @Overrides
//    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
//        Log.d(TAG, "Binding view for position: " + position);
//        Category category = values.get(position);
//        holder.txtCategoryName.setText(category.getStrCategory());
//
//        Glide.with(context).load(category.getStrCategoryThumb()).into(holder.imgCategory);
//
//        holder.imgCategory.setOnClickListener(view -> {
//            Intent intent = new Intent(context, CategoriesMealsActivity.class);
//            String categoryName = category.getStrCategory();
//            intent.putExtra("CATEGORY_NAME", categoryName);
//            context.startActivity(intent);
//        });
//    }
    @Override
    public int getItemCount() {
        return (ingridents != null) ? ingridents.size() : 0;

    }

}

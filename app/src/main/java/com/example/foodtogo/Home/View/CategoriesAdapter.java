package com.example.foodtogo.Home.View;

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
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsActivity;
import com.example.foodtogo.R;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Meal;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final Context context;
    private List<Category> values;
    private static final String TAG = "RecyclerView";

    public CategoriesAdapter(Context context, List<Category> values) {
        this.context = context;
        this.values = values;
    }

    public void setList(List<Category> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView txtCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            txtCategoryName = itemView.findViewById(R.id.txt_category_name);
        }
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.categoryitem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "Binding view for position: " + position);
        Category category = values.get(position);
        holder.txtCategoryName.setText(category.getStrCategory());

        Glide.with(context).load(category.getStrCategoryThumb()).into(holder.imgCategory);

        holder.imgCategory.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoriesMealsActivity.class);
            String categoryName = category.getStrCategory();
            intent.putExtra("CATEGORY_NAME", categoryName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (values != null) ? values.size() : 0;
    }
}

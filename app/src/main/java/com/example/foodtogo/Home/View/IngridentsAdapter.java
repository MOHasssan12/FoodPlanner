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
import com.example.foodtogo.MealsByCountries.View.MealsByCountiresActivity;
import com.example.foodtogo.MealsByIngridents.View.MealsByIngridentsActivity;
import com.example.foodtogo.R;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Ingridents;

import java.util.List;

public class IngridentsAdapter extends RecyclerView.Adapter<IngridentsAdapter.ViewHolder> {

private final Context context;
private List<Ingridents> values;
private static final String TAG = "RecyclerView";

public IngridentsAdapter(Context context, List<Ingridents> values) {
    this.context = context;
    this.values = values;

}

public void setList(List<Ingridents> values) {
    this.values = values;
    notifyDataSetChanged();
}

public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imgIngrident;
    TextView txtIngridentName;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imgIngrident = itemView.findViewById(R.id.img_ingrident);
        txtIngridentName = itemView.findViewById(R.id.txt_ingrident_name);
    }
}

@NonNull
@Override
public IngridentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View itemView = inflater.inflate(R.layout.ingridentitem, parent, false);
    return new IngridentsAdapter.ViewHolder(itemView);
}

@Override
public void onBindViewHolder(@NonNull IngridentsAdapter.ViewHolder holder, int position) {
    Log.d(TAG, "Binding view for ingredient at position: " + position);
    Ingridents ingridents = values.get(position);
    Log.d(TAG, "Ingredient: " + ingridents.getStrIngredient());

    holder.txtIngridentName.setText(ingridents.getStrIngredient());

    String ingredientName = ingridents.getStrIngredient();
    String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientName +".png";

    Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .override(100, 100)
            .into(holder.imgIngrident);

    holder.imgIngrident.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MealsByIngridentsActivity.class);
            intent.putExtra("IngridentName", ingredientName);
            context.startActivity(intent);
        }
    });

}

@Override
public int getItemCount() {
    return (values != null) ? values.size() : 0;
}
}

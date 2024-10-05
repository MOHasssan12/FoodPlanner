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
import com.example.foodtogo.MealsByCategories.View.CategoriesMealsActivity;
import com.example.foodtogo.MealsByCountries.View.MealsByCountiresActivity;
import com.example.foodtogo.R;
import com.example.foodtogo.model.Category;
import com.example.foodtogo.model.Country;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {


    private final Context context;
    private List<Country> values;
    private static final String TAG = "RecyclerView";

    public CountriesAdapter(Context context, List<Country> values) {
        this.context = context;
        this.values = values;
    }

    public void setList(List<Country> values) {
        this.values = values;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCountry;
        TextView txtCountryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCountry = itemView.findViewById(R.id.img_country);
            txtCountryName = itemView.findViewById(R.id.txt_country_name);
        }
    }

    @NonNull
    @Override
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.countryitem, parent, false);
        CountriesAdapter.ViewHolder vh = new CountriesAdapter.ViewHolder(itemView);
        Log.i(TAG, "onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "Binding view for position: " + position);

        Country country = values.get(position);
        holder.txtCountryName.setText(country.getStrArea());
        Log.d(TAG, "Country name: " + country.getStrArea());


        String countryCode = getCountryCode(country.getStrArea());
        String flagUrl = "https://flagcdn.com/160x120/" + countryCode + ".png";

        Glide.with(context)
                .load(flagUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgCountry);
        Log.d(TAG, "Flag URL: " + flagUrl);

        holder.imgCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsByCountiresActivity.class);
                intent.putExtra("CountryName", country.getStrArea());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (values != null) ? values.size() : 0;
    }

    private String getCountryCode(String countryName) {
        switch (countryName) {
            case "American":
                return "us";
            case "British":
                return "gb";
            case "Canadian":
                return "ca";
            case "Chinese":
                return "cn";
            case "Croatian":
                return "hr";
            case "Dutch":
                return "nl";
            case "Egyptian":
                return "eg";
            case "Filipino":
                return "ph";
            case "French":
                return "fr";
            case "Greek":
                return "gr";
            case "Indian":
                return "in";
            case "Irish":
                return "ie";
            case "Italian":
                return "it";
            case "Jamaican":
                return "jm";
            case "Japanese":
                return "jp";
            case "Kenyan":
                return "ke";
            case "Malaysian":
                return "my";
            case "Mexican":
                return "mx";
            case "Moroccan":
                return "ma";
            case "Polish":
                return "pl";
            case "Portuguese":
                return "pt";
            case "Russian":
                return "ru";
            case "Spanish":
                return "es";
            case "Thai":
                return "th";
            case "Tunisian":
                return "tn";
            case "Turkish":
                return "tr";
            case "Ukrainian":
                return "ua";
            case "Vietnamese":
                return "vn";
            default:
                return "eg";
        }
    }

}

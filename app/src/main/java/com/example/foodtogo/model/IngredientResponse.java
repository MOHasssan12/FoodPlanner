package com.example.foodtogo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    public List<Ingridents> ingredients;

    public List<Ingridents> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingridents> ingredients) {
        this.ingredients = ingredients;
    }
}

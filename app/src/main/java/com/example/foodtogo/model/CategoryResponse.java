package com.example.foodtogo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    public List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

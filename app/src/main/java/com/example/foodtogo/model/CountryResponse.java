package com.example.foodtogo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {

    @SerializedName("meals")
    public List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}

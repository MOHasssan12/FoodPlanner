package com.example.foodtogo.model;

public class Country {

    private String strArea;


    @Override
    public String toString() {
        return "Meal{" +
                "name='" + strArea +
                '}';
    }



    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}

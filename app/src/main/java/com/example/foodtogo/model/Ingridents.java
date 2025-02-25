package com.example.foodtogo.model;

public class Ingridents {



    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;

    public Ingridents(String idIngredient, String strIngredient, String strDescription, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    @Override
    public String toString() {
        return "Ingrident{" +
                "id='" + idIngredient + '\'' +
                ", name='" + strIngredient + '\'' +
                ", Type='" + strType + '\'' +
                '}';
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}

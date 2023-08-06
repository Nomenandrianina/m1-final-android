package com.example.m1_final_android;

import org.w3c.dom.Attr;

public class Etape {
    private String id;
    private Attraction attraction;
    private int numero;
    private int duree;
    private String unit;
    private String description;

    public Etape(String id, Attraction attraction, int numero, int duree, String unit, String description) {
        this.id = id;
        this.attraction = attraction;
        this.numero = numero;
        this.duree = duree;
        this.unit = unit;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}

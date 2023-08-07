package com.example.m1_final_android;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    private String id;
    @SerializedName("titre")
    private String titre;
    @SerializedName("details")
    private String details;
    @SerializedName("lien")
    private String lien;

    public Notification(String id, String titre, String details, String lien) {
        this.id = id;
        this.titre = titre;
        this.details = details;
        this.lien = lien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}
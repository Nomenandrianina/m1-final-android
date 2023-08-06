package com.example.m1_final_android;

import com.google.gson.annotations.SerializedName;

public class Notification {

    private String id;

    private Utilisateur utilisateur;
    private String titre;
    private String details;
    private String lien;

    public Notification(Utilisateur utilisateur, String titre, String details, String lien) {
        this.utilisateur = utilisateur;
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
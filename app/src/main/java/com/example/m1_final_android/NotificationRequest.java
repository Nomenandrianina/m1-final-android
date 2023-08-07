package com.example.m1_final_android;

import com.google.gson.annotations.SerializedName;

public class NotificationRequest {



    private String utilisateur;
    @SerializedName("titre")
    private String titre;

    @SerializedName("details")
    private String details;

    @SerializedName("lien")
    private String lien;

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

    public  NotificationRequest(String id, String titre, String details, String lien) {
        this.utilisateur = id;
        this.titre = titre;
        this.details = details;
        this.lien = lien;
    }

    public NotificationRequest(String id){
        this.utilisateur = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
}

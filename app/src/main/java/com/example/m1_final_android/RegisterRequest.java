package com.example.m1_final_android;

public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;

    public RegisterRequest(){}
    public RegisterRequest(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String nom) {
        String[] names = nom.split(" ");
        String firstName = names[names.length - 1];
        this.prenom = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

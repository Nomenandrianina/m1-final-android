package com.example.m1_final_android;

public class Attraction {

    private String id;
    private String type;
    private String localisation;
    private String html_content;

    public Attraction(String id, String type, String localisation, String html) {
        this.id = id;
        this.type = type;
        this.localisation = localisation;
        this.html_content = html;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


    public String getHtml() {
        return html_content;
    }

    public void setHtml(String html) {
        this.html_content = html;
    }


}

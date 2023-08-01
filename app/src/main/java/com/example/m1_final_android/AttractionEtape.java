package com.example.m1_final_android;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class AttractionEtape implements Parcelable {
    private String _id;
    private String Attraction;
    private String nom;
    private int numero;
    private int duree;
    private String unit;
    private String description;
    private List<AttractionSerialize> media;

    protected AttractionEtape(Parcel in) {
        _id = in.readString();
        Attraction = in.readString();
        nom = in.readString();
        numero = in.readInt();
        duree = in.readInt();
        unit = in.readString();
        description = in.readString();
        media = in.createTypedArrayList(AttractionSerialize.CREATOR);
    }

    public static final Creator<AttractionEtape> CREATOR = new Creator<AttractionEtape>() {
        @Override
        public AttractionEtape createFromParcel(Parcel in) {
            return new AttractionEtape(in);
        }

        @Override
        public AttractionEtape[] newArray(int size) {
            return new AttractionEtape[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAttraction() {
        return Attraction;
    }

    public void setAttraction(String attraction) {
        Attraction = attraction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AttractionSerialize> getMedia() {
        return media;
    }

    public void setMedia(List<AttractionSerialize> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(Attraction);
        parcel.writeString(nom);
        parcel.writeInt(numero);
        parcel.writeInt(duree);
        parcel.writeString(unit);
        parcel.writeString(description);
        parcel.writeTypedList(media);
    }
}

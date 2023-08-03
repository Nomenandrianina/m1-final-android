package com.example.m1_final_android;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttractionSerialize implements Serializable,Parcelable {
    @SerializedName("_id")
    private String id;

    @SerializedName("Attraction")
    private String attraction;


    @SerializedName("Etape")
    private String etape;

    @SerializedName("background")
    private String background;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("media_path")
    private String mediaPath;


    public AttractionSerialize(String id, String attraction, String etape, String background, String mediaType, String mediaPath) {
        this.id = id;
        this.attraction = attraction;
        this.etape = etape;
        this.background = background;
        this.mediaType = mediaType;
        this.mediaPath = mediaPath;
    }

    public AttractionSerialize(){}

    protected AttractionSerialize(Parcel in) {
        id = in.readString();
        attraction = in.readString();
        etape = in.readString();
        background = in.readString();
        mediaType = in.readString();
        mediaPath = in.readString();
    }

    public static final Creator<AttractionSerialize> CREATOR = new Creator<AttractionSerialize>() {
        @Override
        public AttractionSerialize createFromParcel(Parcel in) {
            return new AttractionSerialize(in);
        }

        @Override
        public AttractionSerialize[] newArray(int size) {
            return new AttractionSerialize[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }
    public String getEtape() {return etape;}

    public void setEtape(String etape) {this.etape = etape;}

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(attraction);
        parcel.writeString(etape);
        parcel.writeString(background);
        parcel.writeString(mediaType);
        parcel.writeString(mediaPath);
    }
}

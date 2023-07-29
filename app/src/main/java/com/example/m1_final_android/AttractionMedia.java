package com.example.m1_final_android;
import com.google.gson.annotations.SerializedName;

public class AttractionMedia {
    @SerializedName("_id")
    private String id;

    @SerializedName("Attraction")
    private Attraction attraction;

    @SerializedName("background")
    private String background;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("media_path")
    private String mediaPath;


    public AttractionMedia(String id, Attraction attraction, String background, String mediaType, String mediaPath) {
        this.id = id;
        this.attraction = attraction;
        this.background = background;
        this.mediaType = mediaType;
        this.mediaPath = mediaPath;
    }

    public AttractionMedia(){}

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
}

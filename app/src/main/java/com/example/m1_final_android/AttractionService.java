package com.example.m1_final_android;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface AttractionService {
    @GET("attraction/all")
    Call<ArrayList<AttractionMedia>> getAttractionWithBackgroundOne();
}

package com.example.m1_final_android;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttractionService {
    @GET("attraction/all")
    Call<ArrayList<AttractionMedia>> getAttractionWithBackgroundOne();

    @GET("attraction/detail/{id}")
    Call<ArrayList<AttractionEtape>> getAttractionById(@Path("id") String id);
}

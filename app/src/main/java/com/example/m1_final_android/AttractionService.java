package com.example.m1_final_android;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AttractionService {
    @GET("attraction/all")
    Call<ArrayList<AttractionMedia>> getAttractionWithBackgroundOne();

    @GET("attraction/detail/{id}")
    Call<ArrayList<AttractionEtape>> getAttractionById(@Path("id") String id);

    @POST("attraction/update/etape/like")
    Call<String> updateLikesCount(@Body EtapeRequest etapeRequest);
}

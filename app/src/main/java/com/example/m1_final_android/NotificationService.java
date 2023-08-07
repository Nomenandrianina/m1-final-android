package com.example.m1_final_android;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService {

    @POST("Create_notification")
    Call<Notification> Create_notification(@Body NotificationRequest notification);

    @GET("notification_list")
    Call<ArrayList<Notification>> Get_notification(@Query("utilisateurId") String utilisateur);

}

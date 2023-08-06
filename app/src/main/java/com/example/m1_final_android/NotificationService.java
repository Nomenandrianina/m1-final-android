package com.example.m1_final_android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NotificationService {

    @POST("Create_notification")
    Call<Notification> Create_notification(@Body LoginRequest loginRequest);

    @GET("notification_list")
    List<Notification> Get_notification();

}

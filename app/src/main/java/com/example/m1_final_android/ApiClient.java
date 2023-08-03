package com.example.m1_final_android;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.110:4000/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UtilisateurService getService(){
        UtilisateurService utilisateurService = getRetrofit().create(UtilisateurService.class);

        return utilisateurService;
    }

    public static AttractionService getServiceAttraction(){
        AttractionService attractionService = getRetrofit().create(AttractionService.class);

        return attractionService;
    }
}

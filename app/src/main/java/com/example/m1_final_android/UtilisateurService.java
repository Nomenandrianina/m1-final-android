package com.example.m1_final_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UtilisateurService {

    @POST("login")
    Call<LoginReponse> loginUtilisateur(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<RegisterReponse> registerUtilisateur(@Body RegisterRequest registerRequest);


}

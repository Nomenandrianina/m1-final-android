package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnEnregistrer;
    EditText inputCreateNom, inputCreateEmail, inputCreatePassword, inputCreateConfirmePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void RegisterUtilisateur(RegisterRequest registerRequest){
        Call<RegisterReponse> registerReponseCall = ApiClient.getService().registerUtilisateur(registerRequest);
        registerReponseCall.enqueue(new Callback<RegisterReponse>() {
            @Override
            public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {

            }

            @Override
            public void onFailure(Call<RegisterReponse> call, Throwable t) {

            }
        });
    }
}
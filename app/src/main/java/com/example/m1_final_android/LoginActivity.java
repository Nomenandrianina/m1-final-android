package com.example.m1_final_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        TextView noAccount = findViewById(R.id.linkCreerCompte);
        Button btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(inputEmail.getText().toString()) || TextUtils.isEmpty(inputPassword.getText().toString())){
                    progressBar.setVisibility(view.VISIBLE);
                    String messageRequired = "Veuillez remplir tous les champs.";
                    Toast.makeText(LoginActivity.this,messageRequired, Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    LoginRequest loginRequest = new LoginRequest();
                    //inputEmail.getText().toString() inputPassword.getText().toString(),
                    loginRequest.setEmail(inputEmail.getText().toString());
                    loginRequest.setPassword(inputPassword.getText().toString());

                    loginUtilisateur(loginRequest);
                }
            }
        });
    }

    public void loginUtilisateur(LoginRequest loginRequest){
        Call<LoginReponse> loginReponseCall = ApiClient.getService().loginUtilisateur(loginRequest);

        loginReponseCall.enqueue(new Callback<LoginReponse>() {
            @Override
            public void onResponse(Call<LoginReponse> call, Response<LoginReponse> response) {
                if(response.isSuccessful()){

                    LoginReponse loginReponse = response.body();

                    //Stockage des informations de l'utilisateur
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String loginResponseJson = gson.toJson(loginReponse);

                    editor.putString("login_response", loginResponseJson);
                    editor.apply();

                    Intent serviceIntent = new Intent(getBaseContext(), BackgroundService.class);
                    startService(serviceIntent);

                    startActivity(new Intent(LoginActivity.this,HomeActivity.class).putExtra("data", loginReponse));
                    finish();
                    progressBar.setVisibility(View.INVISIBLE);

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    String messageErreur = "Un erreur s'est produit, veuillez réessayer plus tard!";
                    Toast.makeText(LoginActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginReponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                String messageFailure = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, messageFailure, Toast.LENGTH_LONG).show();
            }
        });
    }

}
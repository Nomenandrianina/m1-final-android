package com.example.m1_final_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //masque l'action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        TextView noAccount = findViewById(R.id.linkCreerCompte);
        TextView ForgotPass = findViewById(R.id.linkMotPasseOublie);
        Button btnLogin = findViewById(R.id.btnLogin);

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
                    String messageRequired = "Veuillez remplir tous les champs.";
                    Toast.makeText(LoginActivity.this,messageRequired, Toast.LENGTH_SHORT).show();
                }else{
                    LoginRequest loginRequest = new LoginRequest();
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

                    startActivity(new Intent(LoginActivity.this,HomeActivity.class).putExtra("data", loginReponse));
                    finish();
                }else{
                    String messageErreur = "Un erreur s'est produit, veuillez réessayer plus tard!";
                    Toast.makeText(LoginActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginReponse> call, Throwable t) {
                String messageFailure = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, messageFailure, Toast.LENGTH_LONG).show();
            }
        });
    }
}
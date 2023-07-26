package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    LoginReponse loginReponse;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            loginReponse = (LoginReponse) intent.getSerializableExtra("data");
            username.setText(loginReponse.getNom());
        }
    }
}
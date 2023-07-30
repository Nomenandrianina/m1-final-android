package com.example.m1_final_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logo = findViewById(R.id.image_logo);
         logo.animate().alpha(1).setDuration(0);

         handler = new Handler();
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent intent = new Intent(MainActivity.this, Parametres.class);
                 startActivity(intent);
                 finish();
             }
         }, 4000);
    }
}
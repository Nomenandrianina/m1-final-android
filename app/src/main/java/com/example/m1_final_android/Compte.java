package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Compte extends AppCompatActivity {

    public static final int timer = 2000;
    RelativeLayout button_update;
    TextView buttonText;
    LottieAnimationView buttonanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        //Update
        button_update = findViewById(R.id.button_update);
        buttonText = findViewById(R.id.button_text);
        buttonanimation = findViewById(R.id.button_animation);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing animation
                buttonanimation.setVisibility(v.VISIBLE);
                buttonanimation.playAnimation();

                buttonText.setVisibility(v.GONE);

                new Handler().postDelayed(this::resetButton, timer);
            }

            private void resetButton() {
                buttonanimation.pauseAnimation();
                buttonanimation.setVisibility(View.GONE);
                buttonText.setVisibility(View.VISIBLE);
            }
        });
    }
}
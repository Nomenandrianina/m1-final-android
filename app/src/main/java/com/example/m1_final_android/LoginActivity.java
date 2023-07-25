package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                }
            }
        });
    }
}
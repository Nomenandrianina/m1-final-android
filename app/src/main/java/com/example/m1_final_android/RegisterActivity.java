package com.example.m1_final_android;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        btnEnregistrer = findViewById(R.id.btnSave);
        inputCreateNom = findViewById(R.id.inputCreateNom);
        inputCreateEmail = findViewById(R.id.inputCreateEmail);
        inputCreatePassword = findViewById(R.id.inputCreatePassword);
        inputCreateConfirmePassword = findViewById(R.id.inputCreateConfirmePassword);

        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( TextUtils.isEmpty(inputCreateNom.getText().toString()) || TextUtils.isEmpty(inputCreateEmail.getText().toString()) || TextUtils.isEmpty(inputCreatePassword.getText().toString()) || TextUtils.isEmpty(inputCreateConfirmePassword.getText().toString())){
                    String messageRequired = "Veuillez remplir tous les champs.";
                    Toast.makeText(RegisterActivity.this,messageRequired, Toast.LENGTH_SHORT).show();
                }else{
                    String password = inputCreatePassword.getText().toString();
                    String confirmPassword = inputCreateConfirmePassword.getText().toString();

                    if (password.equals(confirmPassword)){
                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setNom(inputCreateNom.getText().toString());
                        registerRequest.setPrenom(inputCreateNom.getText().toString());
                        registerRequest.setEmail(inputCreateEmail.getText().toString());
                        registerRequest.setPassword(inputCreatePassword.getText().toString());
                        RegisterUtilisateur(registerRequest);
                    }else {
                        String messageConfrome = "Le mot de passe n'est pas conforme!";
                        Toast.makeText(RegisterActivity.this, messageConfrome, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void RegisterUtilisateur(RegisterRequest registerRequest){
        Call<RegisterReponse> registerReponseCall = ApiClient.getService().registerUtilisateur(registerRequest);

        //(String id,String titre,String details, String lien)

        registerReponseCall.enqueue(new Callback<RegisterReponse>() {
            @Override
            public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                if(response.isSuccessful()){

                    String utilisateur= response.body().getId();
                    String details="Creation de compte reussie, bienvenu "+registerRequest.getNom();
                    String titre = "World tour";
                    String lien="";

                    Call<Notification> notificationss = ApiClient.getServiceNotification().Create_notification(new NotificationRequest(utilisateur,titre,details,lien));
                    notificationss.enqueue(new Callback<Notification>() {
                    @Override
                    public void onResponse(Call<Notification> call, Response<Notification> response) {
                       if (response.isSuccessful()) {
                           Notification notification = response.body();
                           System.out.println("reuissie");
                           // Utilisez les valeurs de la notification ici
                           String notificationId = notification.getId();
                           String notificationTitle = notification.getTitre();
                           // ... etc.
                       } else {
                           // La requête n'a pas abouti, traitez les erreurs si nécessaire
                       }
                    }
                    @Override
                    public void onFailure(Call<Notification> call, Throwable t) {
                        // Gestion des erreurs lors de l'appel réseau
                        System.out.println("errer"+ t.getMessage());
                    }

               });

                    showNotification(getBaseContext(),"Inscription reussie");

                    String messageSucces = "Enregistrement réussi!";
                    Toast.makeText(RegisterActivity.this, messageSucces, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else{
                    String messageErreur = "Erreur d'enregistrement!";
                    Toast.makeText(RegisterActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterReponse> call, Throwable t) {
                String messageFailure = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, messageFailure, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Pour les versions Android 8.0 (Oreo) et supérieures, vous devez créer un canal de notification.
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_notification) // L'icône de la notification
                .setContentTitle("World Tour") // Titre de la notification
                .setContentText(message) // Contenu de la notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Afficher la notification
        notificationManager.notify(0, builder.build());
    }

}
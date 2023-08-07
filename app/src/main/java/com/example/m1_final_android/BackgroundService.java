package com.example.m1_final_android;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundService extends Service {
   // private Context context = getBaseContext();

    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;
    private ArrayList<Notification> currentNotifications = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("repetition");
        // Vérifier les nouvelles données
        checkForNewData();

        // Répéter la vérification toutes les minutes
        long intervalMillis = 60 * 1000; // 1 minute
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, pendingIntent);

        return START_STICKY;
    }

    private void checkForNewData() {

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String loginResponseJson = sharedPreferences.getString("login_response", null);
        LoginReponse loginResponse = gson.fromJson(loginResponseJson, LoginReponse.class);
        String id_user = loginResponse.getId();

        Call<ArrayList<Notification>> call = ApiClient.getServiceNotification().Get_notification(id_user);
        call.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Notification> newNotifications = response.body();
                    System.out.println("notif_rep" + newNotifications);
                    // Comparer les données pour détecter les ajouts
                    ArrayList<Notification> addedNotifications = new ArrayList<>();
                    for (Notification newNotification : newNotifications) {
                        if (!currentNotifications.contains(newNotification)) {
                            addedNotifications.add(newNotification);
                        }
                    }

                    // Générer une notification pour les nouveaux utilisateurs
                    if (!addedNotifications.isEmpty()) {
                        showNotification(getBaseContext(),"Creation de nouveau compte");
                    }

                    // Mettre à jour l'état actuel des données
                    currentNotifications = newNotifications;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                // Gestion des erreurs
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

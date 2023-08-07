package com.example.m1_final_android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] parametres = {"Localisation", "Compte", "Langue"};
    private int[] images = {R.drawable.localisation};

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        showNotification(getContext(),"bonjour tout le monde");

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ListView listViewParametres = view.findViewById(R.id.listview_notification);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String loginResponseJson = sharedPreferences.getString("login_response", null);
        LoginReponse loginResponse = gson.fromJson(loginResponseJson, LoginReponse.class);
        String id_user = loginResponse.getId();

        //Appel vers l'api pour avoir la liste des notifications de l'utilisateur
        Call<ArrayList<Notification>> notification = ApiClient.getServiceNotification().Get_notification(id_user);

        notification.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Notification> dataList = response.body();
                    String[] titre = new String[dataList.size()];
                    String[] details = new String[dataList.size()];
                    int[] image = new int[dataList.size()];
                    for (int i = 0; i < dataList.size(); i++) {
                        titre[i] = dataList.get(i).getTitre();
                        details[i] = dataList.get(i).getDetails();
                        image[i] = R.drawable.ic_notification;
                    }

                    NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), titre, details, image);
                    listViewParametres.setAdapter(notificationAdapter);
                    //System.out.println(dataList.size());
                    // Process the list of data here
                } else {
                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                System.out.println("erreur" + t.getMessage());
            }
        });
        return view;
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
                .setContentTitle("test notif") // Titre de la notification
                .setContentText(message) // Contenu de la notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Afficher la notification
        notificationManager.notify(0, builder.build());
    }


}
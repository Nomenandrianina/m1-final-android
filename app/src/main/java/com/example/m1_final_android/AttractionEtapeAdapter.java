package com.example.m1_final_android;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttractionEtapeAdapter extends ArrayAdapter<AttractionEtape> {

    private Context context;
    private int resource;
    private ArrayList<AttractionEtape> attractionEtapes;

    private AttractionService  attractionService;

    public AttractionEtapeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AttractionEtape> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.attractionEtapes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        attractionService = ApiClient.getServiceAttraction();

        ImageView imageDetailList = view.findViewById(R.id.imageDetailList);
        TextView txtDetailName = view.findViewById(R.id.txtDetailName);
        TextView txtDetailEtape = view.findViewById(R.id.txtDetailEtape);
        TextView txtDetailDuree = view.findViewById(R.id.txtDetailDuree);
        TextView txtDetailDescription = view.findViewById(R.id.txtDetailDescription);

        ImageButton btnLike = view.findViewById(R.id.btnLike);
        TextView txtLikesCount = view.findViewById(R.id.txtLikesCount);

        AttractionEtape attractionEtape = attractionEtapes.get(position);

        // Charger l'image et définir les détails dans les TextViews
        // Vous pouvez utiliser les mêmes méthodes pour charger l'image que vous avez utilisées précédemment
        String imageName = attractionEtape.getMedia().get(0).getMediaPath();
//        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
//
//        if (resourceId != 0) {
//            // Affichez l'image dans ImageView
//            imageDetailList.setImageResource(resourceId);
//        } else {
//            // Vous pouvez définir une image par défaut ici
//        }

        byte[] decodedImage = Base64.decode(imageName, Base64.DEFAULT);
        Glide.with(context)
                .load(decodedImage)
                .into(imageDetailList);

        txtDetailName.setText(attractionEtape.getNom());
        txtDetailEtape.setText(String.valueOf(attractionEtape.getNumero()) + " étape(s)");
        txtDetailDuree.setText(String.valueOf(attractionEtape.getDuree()) + " " + attractionEtape.getUnit());
        txtDetailDescription.setText(attractionEtape.getDescription());
        txtLikesCount.setText(String.valueOf(attractionEtape.getLikesCount()));

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mettez en œuvre la logique pour gérer le clic sur le bouton "J'aime"
                // Vous pouvez incrémenter le compteur de "J'aime", envoyer la mise à jour au serveur, etc.
                // Par exemple :
                attractionEtape.incrementLikesCount();
                EtapeRequest etapeRequest = new EtapeRequest();
                //inputEmail.getText().toString() inputPassword.getText().toString(),
                etapeRequest.setEtapeId(attractionEtape.get_id());
                etapeRequest.setLikecount(attractionEtape.getLikesCount());// Incrémentez le nombre de "J'aime" dans le modèle
                updateLikesCountInDatabase(etapeRequest);
                txtLikesCount.setText(String.valueOf(attractionEtape.getLikesCount())); // Mettez à jour l'affichage du nombre de "J'aime"
            }
        });


        return view;
    }

    private void updateLikesCountInDatabase(EtapeRequest etapeRequest) {
        Call<String> call = attractionService.updateLikesCount(etapeRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Mise à jour réussie, vous pouvez effectuer des actions supplémentaires si nécessaire.
                    String messageConfrome = "Update réussie";
                    Toast.makeText(context, messageConfrome, Toast.LENGTH_LONG).show();
                    showNotification(context, "Vous avez aimé cette étape !");
                } else {
                    // Gestion des erreurs de la réponse du serveur.
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Gestion des erreurs lors de l'appel de l'API.
            }
        });
    }

    private void showNotification(Context context,String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Pour les versions Android 8.0 (Oreo) et supérieures, vous devez créer un canal de notification.
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_notification) // L'icône de la notification
                .setContentTitle("Nouveau j'aime !") // Titre de la notification
                .setContentText(message) // Contenu de la notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Afficher la notification
        notificationManager.notify(0, builder.build());
    }

}

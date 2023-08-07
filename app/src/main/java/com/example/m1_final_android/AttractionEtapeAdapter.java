package com.example.m1_final_android;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttractionEtapeAdapter extends ArrayAdapter<AttractionEtape> {

    private Context context;
    private int resource;
    private ArrayList<AttractionEtape> attractionEtapes;

    YouTubePlayerView youTubePlayerView;

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
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        TextView txtDetailName = view.findViewById(R.id.txtDetailName);
        TextView txtDetailEtape = view.findViewById(R.id.txtDetailEtape);
        TextView txtDetailDuree = view.findViewById(R.id.txtDetailDuree);
        TextView txtDetailDescription = view.findViewById(R.id.txtDetailDescription);

        ImageButton btnLike = view.findViewById(R.id.btnLike);
        TextView txtLikesCount = view.findViewById(R.id.txtLikesCount);

        AttractionEtape attractionEtape = attractionEtapes.get(position);

        String mediaType = attractionEtape.getMedia().get(0).getMediaType();
        String mediaPath = attractionEtape.getMedia().get(0).getMediaPath();


        if (mediaType.equals("image")) {

            // Afficher l'image dans ImageView
            byte[] decodedImage = Base64.decode(mediaPath, Base64.DEFAULT);
            Glide.with(context)
                    .load(decodedImage)
                    .into(imageDetailList);
            // Cacher le YouTubePlayerView
            youTubePlayerView.setVisibility(View.GONE);
        } else if (mediaType.equals("video")) {
            // Cacher l'ImageView
            imageDetailList.setVisibility(View.GONE);
            txtDetailEtape.setVisibility(View.GONE);
            txtDetailDuree.setVisibility(View.GONE);
            imageDetailList.setVisibility(View.GONE);

            // Afficher la vidéo dans YouTubePlayerView
            String VideoId = extractYouTubeVideoIdFromUrl(mediaPath); // Méthode pour extraire l'identifiant YouTube à partir du lien
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                    // Charger la vidéo à l'aide de l'ID vidéo YouTube
                    youTubePlayer.loadVideo(VideoId, 0f);
                }
            });
            youTubePlayerView.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.BELOW, R.id.txtDetailName);
            txtDetailDescription.setLayoutParams(params);
        }

        txtDetailName.setText(attractionEtape.getNom());
        txtDetailEtape.setText(String.valueOf(attractionEtape.getNumero()) + " étape(s)");
        txtDetailDuree.setText(String.valueOf(attractionEtape.getDuree()) + " " + attractionEtape.getUnit());
        txtDetailDescription.setText(attractionEtape.getDescription());
        txtLikesCount.setText(String.valueOf(attractionEtape.getLikesCount()));

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private String extractYouTubeVideoIdFromUrl(String youtubeUrl) {
        // Exemple: "https://www.youtube.com/watch?v=Sw09MYhOVWc"
        // Extrait "Sw09MYhOVWc"
        String videoId = youtubeUrl.substring(youtubeUrl.indexOf("?v=") + 3);
        return videoId;
    }

    public void stopVideoPlayback() {
        youTubePlayerView.release();
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

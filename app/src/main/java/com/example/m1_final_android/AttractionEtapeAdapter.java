package com.example.m1_final_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AttractionEtapeAdapter extends ArrayAdapter<AttractionEtape> {

    private Context context;
    private int resource;
    private ArrayList<AttractionEtape> attractionEtapes;

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

        ImageView imageDetailList = view.findViewById(R.id.imageDetailList);
        TextView txtDetailName = view.findViewById(R.id.txtDetailName);
        TextView txtDetailEtape = view.findViewById(R.id.txtDetailEtape);
        TextView txtDetailDuree = view.findViewById(R.id.txtDetailDuree);
        TextView txtDetailDescription = view.findViewById(R.id.txtDetailDescription);

        AttractionEtape attractionEtape = attractionEtapes.get(position);

        // Charger l'image et définir les détails dans les TextViews
        // Vous pouvez utiliser les mêmes méthodes pour charger l'image que vous avez utilisées précédemment
        String imageName = attractionEtape.getMedia().get(0).getMediaPath();
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (resourceId != 0) {
            // Affichez l'image dans ImageView
            imageDetailList.setImageResource(resourceId);
        } else {
            // Vous pouvez définir une image par défaut ici
        }

        txtDetailName.setText(attractionEtape.getNom());
        txtDetailEtape.setText(String.valueOf(attractionEtape.getNumero()) + " étape(s)");
        txtDetailDuree.setText(String.valueOf(attractionEtape.getDuree()) + " " + attractionEtape.getUnit());
        txtDetailDescription.setText(attractionEtape.getDescription());

        return view;
    }
}

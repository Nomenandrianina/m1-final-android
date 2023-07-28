package com.example.m1_final_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m1_final_android.R;

public class ParametresAdapter extends ArrayAdapter<String> {

    private final String[] parametres;
    private final int[] images;

    public ParametresAdapter(Context context, String[] parametres, int[] images) {
        super(context, R.layout.parametres_item, parametres);
        this.parametres = parametres;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.parametres_item, parent, false);
        }

        ImageView imageViewParametre = convertView.findViewById(R.id.imageViewParametre);
        TextView textViewParametre = convertView.findViewById(R.id.textViewParametre);

        imageViewParametre.setImageResource(images[position]);
        textViewParametre.setText(parametres[position]);

        return convertView;
    }
}

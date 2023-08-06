package com.example.m1_final_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationAdapter extends ArrayAdapter<String> {
    private final String[] titre;

    private final String [] details;
    private final int[] images;

    public NotificationAdapter(Context context, String[] parametres,String[]details, int[] images) {
        super(context, R.layout.list_notification, parametres);
        this.titre = parametres;
        this.images = images;
        this.details = details;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_setting, parent, false);
        }

        ImageView imageViewParametre = convertView.findViewById(R.id.icon_notification);
        TextView titre_notification = convertView.findViewById(R.id.titre);
        TextView details_notification = convertView.findViewById(R.id.details);

        imageViewParametre.setImageResource(images[position]);
        titre_notification.setText(titre[position]);
        details_notification.setText(details[position]);

        return convertView;
    }
}

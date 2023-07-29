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

public class AttractionAdapter extends ArrayAdapter<Attraction> {

    private Context mcontext;
    private int mressource;
    public AttractionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Attraction> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mressource,parent,false);

        ImageView imageView = convertView.findViewById(R.id.imageList);

        TextView txtType =  convertView.findViewById(R.id.txtName);

        TextView txtDesc = convertView.findViewById((R.id.txtDescription));


        return convertView;
    }
}

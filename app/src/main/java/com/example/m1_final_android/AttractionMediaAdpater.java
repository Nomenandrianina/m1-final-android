package com.example.m1_final_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.util.ArrayList;

public class AttractionMediaAdpater extends ArrayAdapter<AttractionMedia> {
    private Context mcontext;
    private int mressource;

    public AttractionMediaAdpater(@NonNull Context context, int resource, @NonNull ArrayList<AttractionMedia> objects) {
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

        String imageName = getItem(position).getMediaPath();

//        int resourceId = mcontext.getResources().getIdentifier(imageName, "drawable", mcontext.getPackageName());
//
//        imageView.setImageResource(resourceId);

        byte[] decodedImage = Base64.decode(imageName, Base64.DEFAULT);
        Glide.with(mcontext)
                .load(decodedImage)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(50, 10)))
                .into(imageView);

        txtType.setText(getItem(position).getAttraction().getType());

        txtDesc.setText(getItem(position).getAttraction().getLocalisation());


        return convertView;
    }

    public void updateList(ArrayList<AttractionMedia> newList) {
        clear();
        addAll(newList);
        notifyDataSetChanged();
    }
}

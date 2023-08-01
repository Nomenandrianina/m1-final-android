package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ListView listView;
    private AttractionEtapeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listView = findViewById(R.id.detailListView);

        ArrayList<AttractionEtape> attractionEtapes = getIntent().getParcelableArrayListExtra("attractionEtapes");
        if (attractionEtapes != null && attractionEtapes.size() > 0) {
            adapter = new AttractionEtapeAdapter(this, R.layout.item_attraction_etape, attractionEtapes);
            listView.setAdapter(adapter);
        }

        ImageView btnBack = findViewById(R.id.returnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Appeler la méthode onBackPressed() pour revenir en arrière
            }
        });
    }
}
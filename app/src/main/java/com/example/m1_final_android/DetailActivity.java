package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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

        Uri fileUri = getIntent().getParcelableExtra("dataUri");
        ArrayList<AttractionEtape> attractionEtapes = readDataFromFile(fileUri);

//        ArrayList<AttractionEtape> attractionEtapes = getIntent().getParcelableArrayListExtra("attractionEtapes");
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

    // Méthode pour lire les données depuis le fichier
    private ArrayList<AttractionEtape> readDataFromFile(Uri fileUri) {
        ArrayList<AttractionEtape> attractionEtapes = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            attractionEtapes = (ArrayList<AttractionEtape>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return attractionEtapes;
    }
}
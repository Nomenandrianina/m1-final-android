package com.example.m1_final_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Parametres extends AppCompatActivity {

    private String[] parametres = {"Localisation", "Compte", "Langue"};
    private int[] images = {R.drawable.localisation, R.drawable.compte, R.drawable.langue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        ListView listViewParametres = findViewById(R.id.listViewParametres);

            ParametresAdapter adapter = new ParametresAdapter(this,parametres, images);
        listViewParametres.setAdapter(adapter);

        listViewParametres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Gérer la redirection vers les pages correspondantes ici
                // Par exemple, ouvrir une nouvelle activité pour chaque paramètre :
                switch (position) {
                    case 0: // Localisation
                        //startActivity(new Intent(ParametresActivity.this, LocalisationActivity.class));
                        break;
                    case 1: // Compte
                        //startActivity(new Intent(ParametresActivity.this, CompteActivity.class));
                        break;
                    case 2: // Langue
                        //startActivity(new Intent(ParametresActivity.this, LangueActivity.class));
                        break;
                    default:
                        Toast.makeText(Parametres.this, "Paramètre non implémenté.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}

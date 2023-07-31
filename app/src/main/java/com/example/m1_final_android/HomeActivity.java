package com.example.m1_final_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.m1_final_android.databinding.ActivityHomeBinding;
import com.example.m1_final_android.databinding.AppbarBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    LoginReponse loginReponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        remplaceFragment(new NearFragment());

        // Accéder au SearchView
        SearchView searchView = findViewById(R.id.searchView);

        // Ajouter un listener pour gérer les événements de recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Action lorsque l'utilisateur soumet la recherche (touche Entrée)
//                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                // Action lorsque le texte de recherche change (à chaque saisie)
                // Vous pouvez implémenter ici une recherche en temps réel si nécessaire
                return false;
            }
        });


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.near:
                    remplaceFragment(new NearFragment());
                    break;
                case R.id.circuit:
                    remplaceFragment(new CircuitFragment());
                    break;
                case R.id.setting:
                    remplaceFragment(new SettingFragment());
                    break;
            }

           return true;
        });

    }


    private void performSearch(String query) {
        NearFragment nearFragment = (NearFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (nearFragment != null) {
            nearFragment.onSearch(query);
        }
    }


    private void remplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

}
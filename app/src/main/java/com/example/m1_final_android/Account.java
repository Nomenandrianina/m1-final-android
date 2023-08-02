package com.example.m1_final_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private UtilisateurService utilisateurservice;

    private String mParam1;
    private String mParam2;
    public TextView nom;
    public TextView prenom;
    public TextInputEditText nom_valeur;
    public TextInputEditText prenom_valeur;
    public TextInputEditText email_valeur;



    public static final int timer = 2000;
    RelativeLayout button_update;
    TextView buttonText;
    LottieAnimationView buttonanimation;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void updateUser(Utilisateur updatedUser) {
        Call<Utilisateur> call = ApiClient.getService().Update_utilisateur(updatedUser);
        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Utilisateur updatedUser = response.body();
                    ReloadFragment();
                   // callback.onUpdateSuccess(updatedUser);
                } else {
                    Toast.makeText(getContext(), "messageFailure", Toast.LENGTH_LONG).show();
                    // callback.onUpdateFailed("Update failed");
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                String messageFailure = t.getLocalizedMessage();
                Toast.makeText(getContext(), messageFailure, Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface UpdateUserCallback {
        void onUpdateSuccess(Utilisateur updatedUser);
        void onUpdateFailed(String errorMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String loginResponseJson = sharedPreferences.getString("login_response", null);

        if(loginResponseJson!=null){
            LoginReponse loginResponse = gson.fromJson(loginResponseJson, LoginReponse.class);
            System.out.println(loginResponse);
            Log.e("data","logreponse "+ loginResponse.getNom());

            nom = view.findViewById(R.id.nom_utilisateur);
            nom.setText(loginResponse.getNom());
            prenom = view.findViewById(R.id.prenom);
            prenom.setText(loginResponse.getPrenom());

            nom_valeur = view.findViewById(R.id.nom_value);
            nom_valeur.setText(loginResponse.getNom());

            prenom_valeur = view.findViewById(R.id.prenom_value);
            prenom_valeur.setText(loginResponse.getPrenom());

            email_valeur = view.findViewById(R.id.email_value);
            email_valeur.setText(loginResponse.getEmail());
        }

        button_update = view.findViewById(R.id.button_update);
        buttonText = view.findViewById(R.id.button_text);
        buttonanimation = view.findViewById(R.id.button_animation);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing animation
                buttonanimation.setVisibility(v.VISIBLE);
                buttonanimation.playAnimation();
                buttonText.setVisibility(v.GONE);

                //Get id
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                Gson gson = new Gson();
                String loginResponseJson = sharedPreferences.getString("login_response", null);
                LoginReponse loginResponse = gson.fromJson(loginResponseJson, LoginReponse.class);
                String id_user = loginResponse.getId();

                nom_valeur = view.findViewById(R.id.nom_value);
                prenom_valeur = view.findViewById(R.id.prenom_value);
                email_valeur = view.findViewById(R.id.email_value);
                Utilisateur update_value= new Utilisateur(loginResponse.getId(),nom_valeur.getText().toString(),prenom_valeur.getText().toString(),email_valeur.getText().toString());
                updateUser(update_value);

                new Handler().postDelayed(this::resetButton, timer);


            }

            private void resetButton() {
                buttonanimation.pauseAnimation();
                buttonanimation.setVisibility(View.GONE);
                buttonText.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    // Fonction qui recharge dans la même page
    private void ReloadFragment() {
        // Reload the fragment
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
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

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextView nom;
    public TextView prenom;

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
}
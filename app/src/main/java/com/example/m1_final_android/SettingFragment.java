package com.example.m1_final_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String[] parametres = {"Localisation", "Compte", "Langue"};
    private int[] images = {R.drawable.localisation, R.drawable.compte, R.drawable.langue};


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ListView listViewParametres = view.findViewById(R.id.listViewsetting);
        ParametresAdapter adapter = new ParametresAdapter(requireContext(),parametres, images);
        listViewParametres.setAdapter(adapter);

        listViewParametres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Gérer la redirection vers les pages correspondantes ici
                // Par exemple, ouvrir une nouvelle activité pour chaque paramètre :
                switch (position) {
                    case 0: // Localisation

                        break;
                    case 1: // Compte
                        // ouverture du fragment compte
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_parametre, new Account());
                        fragmentTransaction.addToBackStack(null); // Add to back stack, so user can navigate back
                        fragmentTransaction.commit();
                        break;
                    case 2: // Langue
                        //startActivity(new Intent(ParametresActivity.this, LangueActivity.class));
                        break;
                    default:
                        Toast.makeText(getContext(), "Paramètre non implémenté.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return view;
    }
}
package com.example.m1_final_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearFragment extends Fragment implements OnSearchListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String NO_RESULTS_TEXT = "Aucun élément trouvé";

    ListView listView;
    AttractionService attractionService;

    ArrayList<AttractionMedia> listAttractionMedia = new ArrayList<>();

    ArrayList<AttractionMedia> fullListAttractionMedia = new ArrayList<>();

    AttractionMediaAdpater attractionMediaAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearFragment newInstance(String param1, String param2) {
        NearFragment fragment = new NearFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_near, container, false);

        // Récupérez votre ListView à partir du layout du fragment_near.xml
        listView = rootView.findViewById(R.id.listView);

        attractionService = ApiClient.getServiceAttraction();
        loadDataAttractionMedia();


        return rootView;
    }

    public void loadDataAttractionMedia(){
        // Appelez la méthode pour obtenir les attractions avec background 1
        Call<ArrayList<AttractionMedia>> dataAttraction = attractionService.getAttractionWithBackgroundOne();

        dataAttraction.enqueue(new Callback<ArrayList<AttractionMedia>>() {
            @Override
            public void onResponse(Call<ArrayList<AttractionMedia>> call, Response<ArrayList<AttractionMedia>> response) {
                if (response.isSuccessful()) {
                    // Récupérez les données renvoyées par le serveur
                    ArrayList<AttractionMedia> attractionMediaResponse = response.body();

                    // Remplissez votre liste d'objets Attraction à partir des données renvoyées par le serveur
                    for (AttractionMedia attractionMedia : attractionMediaResponse) {
                        listAttractionMedia.add(attractionMedia);
                    }

                    fullListAttractionMedia.addAll(listAttractionMedia);


                    // Créez l'adapter pour afficher les données dans la ListView
                    attractionMediaAdapter = new AttractionMediaAdpater(requireContext(), R.layout.list_row, listAttractionMedia);
                    listView.setAdapter(attractionMediaAdapter);

                    Log.e("API LOG", "Liste des attractions Medias :"+ listAttractionMedia);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AttractionMedia selectedItem = (AttractionMedia) parent.getItemAtPosition(position);
                            Log.e("API LOG", "Selected Item :"+ selectedItem.getAttraction().getId());
                            // Handle the item click here (e.g., show detail activity)
                            openDetailActivity(selectedItem);
                        }
                    });

                } else {
                    Log.e("API Error", "Erreur lors de la récupération des données.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AttractionMedia>> call, Throwable t) {
                Log.e("API Error", "Erreur lors de l'appel de l'API: " + t.getMessage());
            }
        });
    }




    private void openDetailActivity(AttractionMedia attractionMedia){
        Call<ArrayList<AttractionEtape>> dataAttractionEtape = attractionService.getAttractionById(attractionMedia.getAttraction().getId());
        dataAttractionEtape.enqueue(new Callback<ArrayList<AttractionEtape>>() {
            @Override
            public void onResponse(Call<ArrayList<AttractionEtape>> call, Response<ArrayList<AttractionEtape>> response) {
                if (response.isSuccessful()){
                    // Récupérez les données renvoyées par le serveur
                    ArrayList<AttractionEtape> attractionEtapeResponse = response.body();
                    Log.e("API", "Parcelable response."+ attractionEtapeResponse.get(0).getMedia());
                    Intent intent = new Intent(requireContext(), DetailActivity.class);
                    intent.putParcelableArrayListExtra("attractionEtapes",  attractionEtapeResponse);
                    startActivity(intent);
                }else {
                    Log.e("API Error", "Erreur lors de la récupération des données.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AttractionEtape>> call, Throwable t) {
                Log.e("API Error", "Erreur lors de l'appel de l'API: " + t.getMessage());
            }
        });
    }

    @Override
    public void onSearch(String query) {
        if (TextUtils.isEmpty(query)) {
            attractionMediaAdapter.updateList(fullListAttractionMedia);
        } else {
            // Sinon, effectuer la recherche en fonction de la chaîne de recherche "query"

            // Par exemple, vous pouvez filtrer la liste d'attractions en fonction du nom ou d'autres critères
            ArrayList<AttractionMedia> filteredList = new ArrayList<>();
            for (AttractionMedia attractionMedia : listAttractionMedia) {
                if (attractionMedia.getAttraction().getType().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(attractionMedia);
                }
            }

            // Mettez à jour l'adapter avec la nouvelle liste filtrée
            attractionMediaAdapter.updateList(filteredList);
        }
    }
}
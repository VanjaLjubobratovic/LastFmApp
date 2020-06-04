package com.asseco.zadatak1.SearchScreen;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.asseco.zadatak1.ArtistDetails.ArtistDetailsFragment;
import com.asseco.zadatak1.Home.HomeFragment;
import com.asseco.zadatak1.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchFragment extends Fragment {

    private SearchViewModel mSearchViewModel;
    private SearchView search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        search = view.findViewById(R.id.searchBar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchViewModel.init(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        mSearchViewModel.getArtist().observe(getViewLifecycleOwner(), new Observer<ArtistSearchModel>() {
            @Override
            public void onChanged(ArtistSearchModel artistSearchModel) {
               if(artistSearchModel != null) {
                    Fragment fr = new ArtistDetailsFragment();
                    Bundle artistData = new Bundle();
                    artistData.putString("name", artistSearchModel.getName());
                    artistData.putString("mbid", artistSearchModel.getMbid());
                    mSearchViewModel.clearData();
                    fr.setArguments(artistData);
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_place, fr).addToBackStack(null).commit();
                }
            }
        });
    }
}

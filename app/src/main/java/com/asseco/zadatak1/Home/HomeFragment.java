package com.asseco.zadatak1.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asseco.zadatak1.R;
import com.asseco.zadatak1.SearchScreen.SearchFragment;
import com.asseco.zadatak1.TopArtists.TopArtistsFragment;
import com.asseco.zadatak1.TopTracks.TopTracksFragment;


public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final Button artistsBtn = view.findViewById(R.id.artistsBtn);
        final Button tracksBtn = view.findViewById(R.id.tracksBtn);
        final Button searchBtn = view.findViewById(R.id.searchBtn);

        artistsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_place, new TopArtistsFragment()).addToBackStack(null);
                fr.commit();
            }
        });

        tracksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_place, new TopTracksFragment()).addToBackStack(null);
                fr.commit();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_place, new SearchFragment()).addToBackStack(null);
                fr.commit();
            }
        });



        return view;
    }

}

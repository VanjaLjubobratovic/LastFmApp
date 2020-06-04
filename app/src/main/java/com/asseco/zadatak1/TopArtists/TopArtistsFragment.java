package com.asseco.zadatak1.TopArtists;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asseco.zadatak1.Home.HomeFragment;
import com.asseco.zadatak1.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class TopArtistsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArtistRecyclerViewAdapter adapter;
    private TopArtistsViewModel mArtistsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_top_artists, container, false);
       recyclerView = view.findViewById(R.id.artistRecyclerView);

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArtistsViewModel = ViewModelProviders.of(this).get(TopArtistsViewModel.class);

        mArtistsViewModel.init();

        mArtistsViewModel.getArtists().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArtistData>>() {
            @Override
            public void onChanged(ArrayList<ArtistData> artistData) {
                adapter.setData(artistData);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        if(adapter == null) {
            adapter = new ArtistRecyclerViewAdapter(mArtistsViewModel.getArtists().getValue(), getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else adapter.notifyDataSetChanged();
    }
}

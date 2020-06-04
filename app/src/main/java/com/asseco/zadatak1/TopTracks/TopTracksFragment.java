package com.asseco.zadatak1.TopTracks;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asseco.zadatak1.Home.HomeFragment;
import com.asseco.zadatak1.R;

import java.util.ArrayList;

public class TopTracksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrackRecyclerViewAdapter adapter;
    private TopTracksViewModel mTracksViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_tracks, container,false);
        recyclerView = view.findViewById(R.id.topTracksRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTracksViewModel = ViewModelProviders.of(this).get(TopTracksViewModel.class);
        mTracksViewModel.init();
        mTracksViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<ArrayList<TrackData>>() {
            @Override
            public void onChanged(ArrayList<TrackData> trackData) {
                adapter.setData(trackData);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        if(adapter == null) {
            adapter = new TrackRecyclerViewAdapter(mTracksViewModel.getTracks().getValue(), getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else adapter.notifyDataSetChanged();
    }
}

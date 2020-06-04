package com.asseco.zadatak1.TopTracks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TopTracksViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TrackData>> mtracks;
    private TrackDataRepository mRepo;

    public void init() {
        if(mtracks != null)
                return;
        mRepo = TrackDataRepository.getInstance();
        mtracks = mRepo.getTracks();
    }

    public LiveData<ArrayList<TrackData>> getTracks() {return mtracks;}
}

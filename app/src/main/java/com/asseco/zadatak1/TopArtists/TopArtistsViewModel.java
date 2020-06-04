package com.asseco.zadatak1.TopArtists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TopArtistsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ArtistData>> mArtists;
    private ArtistDataRepository mRepo;

    public void init() {
        if(mArtists != null)
                return;
        mRepo = ArtistDataRepository.getInstance();
        mArtists = mRepo.getArtists();
    }

    public LiveData<ArrayList<ArtistData>> getArtists() {
        return mArtists;
    }
}

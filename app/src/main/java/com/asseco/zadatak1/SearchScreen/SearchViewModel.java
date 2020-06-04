package com.asseco.zadatak1.SearchScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArtistSearchModel> mArtist = new MutableLiveData<>();
    private SearchRepository mRepo;

    public void init(String query) {
        mRepo = SearchRepository.getInstance();
        mArtist.setValue(mRepo.getArtist(query).getValue());
    }

    public LiveData<ArtistSearchModel> getArtist() {
        return mArtist;
    }

    public void clearData() {
        mArtist.setValue(null);
    }
}

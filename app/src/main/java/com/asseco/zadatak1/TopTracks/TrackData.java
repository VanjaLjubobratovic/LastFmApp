package com.asseco.zadatak1.TopTracks;

public class TrackData {
    private String trackName;
    private String listeners;
    private String playCount;
    private String artistName;
    private String imageUrl;
    //private ArtistData artist  maybe za nesto


    public String getTrackName() {
        return trackName;
    }

    public String getListeners() {
        return listeners;
    }

    public String getPlayCount() {
        return playCount;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public TrackData(String trackName, String listeners, String playCount, String artistName, String imageUrl) {
        this.trackName = trackName;
        this.listeners = listeners;
        this.playCount = playCount;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }
}

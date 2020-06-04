package com.asseco.zadatak1.TopArtists;

public class ArtistData {
    private String playCount;
    private String listeners;
    private String mbid;
    private String name;
    private String imageLink;

    String getPlayCount() {
        return playCount;
    }

    String getListeners() {
        return listeners;
    }

    String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    String getImageLink() {
        return imageLink;
    }

    ArtistData(String mbid, String playCount, String listeners, String name, String imageLink) {
        this.mbid = mbid;
        this.playCount = playCount;
        this.listeners = listeners;
        this.name = name;
        this.imageLink = imageLink;
    }
}

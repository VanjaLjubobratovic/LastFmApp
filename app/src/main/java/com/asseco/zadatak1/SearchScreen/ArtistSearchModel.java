package com.asseco.zadatak1.SearchScreen;

public class ArtistSearchModel {
    private String name;
    private String mbid;

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public ArtistSearchModel(String name, String mbid) {
        this.name = name;
        this.mbid = mbid;
    }
}

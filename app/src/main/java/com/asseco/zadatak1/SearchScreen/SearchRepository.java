package com.asseco.zadatak1.SearchScreen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchRepository {
    private ArtistSearchModel dataSet;
    private MutableLiveData<ArtistSearchModel> data = new MutableLiveData<>();

    public static SearchRepository getInstance() {
        return new SearchRepository();
    }

    public MutableLiveData<ArtistSearchModel> getArtist(String query) {
        setArtist(query);

        data.setValue(dataSet);
        return data;
    }

    private void setArtist(String query) {
        final String requestUrl = "https://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + query.toLowerCase().replaceAll(" ", "+") +
                "&api_key=2123b8e156db10ac7a5065fad9c01b8e&format=json";
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                    parseData(response.body().string());
            }
        });
    }

    private void parseData(String queryResponse) {
        try {
            JSONObject ar = new JSONObject(queryResponse);
            JSONObject artistResponse = new JSONObject(queryResponse).getJSONObject("results").getJSONObject("artistmatches").getJSONArray("artist").getJSONObject(0);
            dataSet = new ArtistSearchModel(artistResponse.getString("name"), artistResponse.getString("mbid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

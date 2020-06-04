package com.asseco.zadatak1.TopTracks;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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

public class TrackDataRepository {
    private static TrackDataRepository instance;
    private ArrayList<TrackData> dataSet = new ArrayList<>();
    private MutableLiveData<ArrayList<TrackData>> data = new MutableLiveData<>();

    public static TrackDataRepository getInstance() {
        if(instance == null)
            instance = new TrackDataRepository();
        return instance;
    }

    public MutableLiveData<ArrayList<TrackData>> getTracks() {
        setTracks();

        data.setValue(dataSet);
        return data;
    }

    private void setTracks() {
        fetchData();
    }

    private void fetchData() {
        final String requestUrl = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=2123b8e156db10ac7a5065fad9c01b8e&format=json";
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("NETWORK FAILURE", "Track fetch");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                    parseData(response.body().string());
                else Log.d("OKHTTP", "Response not successful");
            }
        });
    }

    private void parseData(String queryResponse) {
        try {
            JSONArray trackResponse = new JSONObject(queryResponse).getJSONObject("tracks").getJSONArray("track");

            for(int i = 0; i < trackResponse.length(); i++) {
                JSONObject track = trackResponse.getJSONObject(i);

                dataSet.add(new TrackData(track.getString("name"), track.getString("listeners"), track.getString("playcount"),
                        track.getJSONObject("artist").getString("name"), track.getJSONArray("image").getJSONObject(0).getString("#text")));
            }
            data.postValue(dataSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

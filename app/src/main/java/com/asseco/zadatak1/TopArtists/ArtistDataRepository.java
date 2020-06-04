package com.asseco.zadatak1.TopArtists;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.asseco.zadatak1.MainActivity;
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

public class ArtistDataRepository {
    private static ArtistDataRepository instance;
    private ArrayList<ArtistData> dataSet = new ArrayList<>();
    private MutableLiveData<ArrayList<ArtistData>> data = new MutableLiveData<>();

    public static ArtistDataRepository getInstance() {
        if(instance == null)
            instance = new ArtistDataRepository();
        return instance;
    }

    public MutableLiveData<ArrayList<ArtistData>> getArtists() {
        setArtists();


        data.setValue(dataSet);
        return data;
    }

    public void setArtists () {
        fetchData();
    }

    private void fetchData () {
        final String requestUrl = "https://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=2123b8e156db10ac7a5065fad9c01b8e&format=json";
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("NETWORK FAILURE", "Artist fetch");
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
            JSONArray artistResponse = new JSONObject(queryResponse).getJSONObject("artists").getJSONArray("artist");

            for(int i = 0; i < artistResponse.length(); i++) {
                JSONObject artist = artistResponse.getJSONObject(i);

                dataSet.add(new ArtistData(artist.getString("mbid"), artist.getString("playcount"), artist.getString("listeners"),
                        artist.getString("name"), artist.getJSONArray("image").getJSONObject(0).getString("#text")));
            }
            data.postValue(dataSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

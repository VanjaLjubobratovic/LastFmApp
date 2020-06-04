package com.asseco.zadatak1.ArtistDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asseco.zadatak1.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ArtistDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String artistName;
    private String artistMbid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArtistDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ArtistDetailsFragment newInstance(String param1, String param2) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            artistName = this.getArguments().getString("name");
            artistMbid = this.getArguments().getString("mbid");

            if(artistName.equals("Drake")) //LastFm jednostavno daje krivi ID
                artistMbid = "9fff2f8a-21e6-47de-a2b8-7f449929d43f";

            Log.d("ARTIST NAME", artistName);
            Log.d("ARTIST MBID", artistMbid);
        } else Log.d("Arguments", "no args");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);

        TextView titleName = view.findViewById(R.id.textView);
        final ImageView artistImageView = view.findViewById(R.id.artistDetailsImage);
        final TextView artistData = view.findViewById(R.id.artistData);
        final TextView artistTracks = view.findViewById(R.id.artistTracks);

        titleName.setText(artistName);

        final String artistInfoUrl = "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artistName.replace(" ", "+") + "&api_key=" + getResources().getString(R.string.apiKey) + "&format=json";
        final String artistImageUrl = "https://webservice.fanart.tv/v3/music/" + artistMbid + "&?api_key=3ed8b4c9f29129823fa412ca490575bf&format=json";
        final String artistTracksUrl = "https://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + artistName.replace(" ", "+") + "&api_key=" + getResources().getString(R.string.apiKey) + "&format=json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(artistInfoUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("REQUEST LINK", artistInfoUrl);
                Log.d("NETWORK INFO", "network request unsuccessful");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                final String artistInfoResponse = response.body().string();
                ArtistDetailsFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String artistSummary = new JSONObject(artistInfoResponse).getJSONObject("artist").getJSONObject("bio").getString("summary");
                            artistData.setText(artistSummary);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        request = new Request.Builder().url(artistTracksUrl).build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("NETWORK INFO", "network request unsuccessful");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String artistTracksResponse = response.body().string();
                ArtistDetailsFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray artistTracksArray = new JSONObject(artistTracksResponse).getJSONObject("toptracks").getJSONArray("track");
                            for(int i = 0; i < 3; i++)
                                artistTracks.append("\n" + artistTracksArray.getJSONObject(i).getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        if(artistMbid.length() != 0) {
            request = new Request.Builder().url(artistImageUrl).build();
            call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("NETWORK INFO", "network request unsuccessful");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String artistImageResponse = response.body().string();
                    ArtistDetailsFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String artistImage = new JSONObject(artistImageResponse).getJSONArray("artistthumb").getJSONObject(0).getString("url");
                                Glide.with(getActivity()).asBitmap().load(artistImage).into(artistImageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } else Glide.with(getActivity()).asBitmap().load("https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png").into(artistImageView);

        return view;
    }
}

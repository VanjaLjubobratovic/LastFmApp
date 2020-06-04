package com.asseco.zadatak1.TopTracks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asseco.zadatak1.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrackRecyclerViewAdapter extends RecyclerView.Adapter<TrackRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<TrackData> tracks = new ArrayList<>();

    private Context context;


    TrackRecyclerViewAdapter(ArrayList<TrackData> tracks, Context context) {
        this.tracks.addAll(tracks);
        this.context = context;
    }

    public void setData(ArrayList<TrackData> tracks) {
        this.tracks.clear();
        this.tracks.addAll(tracks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_track_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder called.");

        Glide.with(context).asBitmap().load(tracks.get(position).getImageUrl()).into(holder.image);
        holder.track.setText("Track name: " + tracks.get(position).getTrackName());
        holder.artist.setText("Artist: " + tracks.get(position).getArtistName());
        holder.playCount.setText("Play Count: " + tracks.get(position).getPlayCount());
        holder.listeners.setText("Listeners: " + tracks.get(position).getListeners());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView artist, playCount, listeners, track;
        RelativeLayout itemParentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.trackImage);
            track = itemView.findViewById(R.id.trackName);
            playCount = itemView.findViewById(R.id.trackPlayCount);
            listeners = itemView.findViewById(R.id.trackListeners);
            artist = itemView.findViewById(R.id.trackArtist);
            itemParentLayout = itemView.findViewById(R.id.trackItemParentLayout);
        }
    }
}

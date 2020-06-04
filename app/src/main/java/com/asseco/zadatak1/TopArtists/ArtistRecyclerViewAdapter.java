package com.asseco.zadatak1.TopArtists;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.asseco.zadatak1.ArtistDetails.ArtistDetailsFragment;
import com.asseco.zadatak1.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "ArtistRecyclerView";
    private ArrayList<ArtistData> artists = new ArrayList<>();

    private Context context;

    ArtistRecyclerViewAdapter(ArrayList<ArtistData> artists, Context context) {
        this.context = context;
        this.artists.addAll(artists);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_artist_item, parent, false);
        return new ViewHolder(view);
    }

    public void setData (ArrayList<ArtistData> artists) {
        this.artists.clear();
        this.artists.addAll(artists);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder called.");

        Glide.with(context).asBitmap().load(artists.get(position).getImageLink()).into(holder.image);
        holder.artist.setText("Artist: " + artists.get(position).getName());
        holder.playCount.setText("Play Count: " + artists.get(position).getPlayCount());
        holder.listeners.setText("Listeners: " + artists.get(position).getListeners());

        holder.itemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + artists.get(position).getName());
                Toast.makeText(context, artists.get(position).getName(), Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fr = new ArtistDetailsFragment();
                Bundle artistData = new Bundle();
                artistData.putString("name", artists.get(position).getName());
                artistData.putString("mbid", artists.get(position).getMbid());
                fr.setArguments(artistData);

                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_place, fr).
                        addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView artist, playCount, listeners;
        RelativeLayout itemParentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.artistImage);
            playCount = itemView.findViewById(R.id.artistPlayCount);
            listeners = itemView.findViewById(R.id.artistListeners);
            artist = itemView.findViewById(R.id.artistName);

            itemParentLayout = itemView.findViewById(R.id.artistItemParentLayout);
        }
    }
}

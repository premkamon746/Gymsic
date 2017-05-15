package com.gymsic.kara.gymsic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymsic.kara.gymsic.SearchItemFragment.OnListFragmentInteractionListener;
import com.gymsic.kara.gymsic.data.Song;
import com.gymsic.kara.gymsic.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySearchItemRecyclerViewAdapter extends RecyclerView.Adapter<MySearchItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Song> mSongs;
    private final OnListFragmentInteractionListener mListener;

    public MySearchItemRecyclerViewAdapter(ArrayList<Song> songs, OnListFragmentInteractionListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_searchitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mSongs.get(position);
        holder.song.setText(mSongs.get(position).getName()+ " - "+mSongs.get(position).getArtist());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView song;
        public Song mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            song = (TextView) view.findViewById(R.id.song);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + song.getText() + "'";
        }
    }
}

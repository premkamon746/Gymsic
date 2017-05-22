package com.gymsic.kara.gymsic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gymsic.kara.gymsic.Adapter.MySongRecyclerViewAdapter;
import com.gymsic.kara.gymsic.Interface.OnTaskComplete;
import com.gymsic.kara.gymsic.Model.Song;
import com.gymsic.kara.gymsic.Listener.RecyclerItemClickListener;
import com.gymsic.kara.gymsic.Plugin.Download;
import com.gymsic.kara.gymsic.Plugin.Playlist;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SongFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    ArrayList<Song> songs;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SongFragment newInstance(ArrayList<Song> songs) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putSerializable("songs", songs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songs = (ArrayList<Song>)getArguments().getSerializable("songs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            final LinearLayout layout = (LinearLayout)getActivity().findViewById(R.id.playListHead);

            //get haft
            final int heightDp = getResources().getDisplayMetrics().heightPixels / 2;

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            ProgressBar pb = (ProgressBar)view.findViewById(R.id.progressBar);

                            OnTaskComplete cmp = new OnTaskComplete() {
                                @Override
                                public void onTaskCompleted(Song song) {
                                    Playlist pl = new Playlist(getActivity());
                                    ArrayList<Song> songs = pl.get();

                                    if(songs == null){
                                        songs = new ArrayList<Song>();
                                    }
                                    songs.add(song);
                                    pl.set(songs);

                                    layout.getLayoutParams().height = heightDp;
                                    final PlaylistFragment b =  PlaylistFragment.newInstance(songs);
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                                    ft.replace(R.id.fragment_playlist,b);
                                    ft.commit();
                                }
                            };
                            new Download(getActivity(),pb,songs.get(position),cmp).execute("http://192.168.1.153/mp3db/"+songs.get(position).getFilename());
                            //Log.d("position click : ", songs.get(position).getFilename());
                        }
                    })
            );

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            recyclerView.setAdapter(new MySongRecyclerViewAdapter(songs, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Song item);
    }
}

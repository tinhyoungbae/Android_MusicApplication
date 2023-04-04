package com.example.music.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.music.DatabaseActivity;
import com.example.music.MusicAdapter;
import com.example.music.MusicPlayerActivity;
import com.example.music.R;
import com.example.music.Song;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        /*ArrayList<Song> playlist;
        playlist = new ArrayList<>();

        ListView get_playlist_listview = (ListView) view.findViewById(R.id.playlist_listview);
        ArrayList arrayList = new ArrayList();
        arrayList = MusicPlayerActivity.playList;
        MusicAdapter playListAdapter = new MusicAdapter(getContext(), R.id.song_layout, arrayList);
        if(MusicPlayerActivity.playList != null && !MusicPlayerActivity.playList.isEmpty()){
            for(int i = 0; i < MusicPlayerActivity.playList.size(); i++){
                playlist.add(new Song(MusicPlayerActivity.playList.get(0).toString(), MusicPlayerActivity.playList.get(1).toString(), MusicPlayerActivity.playList.get(2).toString()));
            }
        }
        System.out.println("playlist: " +playlist);
         */
        ;
    }
}
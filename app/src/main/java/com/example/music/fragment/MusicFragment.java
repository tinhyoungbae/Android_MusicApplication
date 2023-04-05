package com.example.music.fragment;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music.DatabaseActivity;
import com.example.music.MusicAdapter;
import com.example.music.MusicPlayerActivity;
import com.example.music.R;
import com.example.music.RegisterActivity;
import com.example.music.Song;
import com.example.music.addToPlayList;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MusicFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String[] items;
    static List<String> artistList;
    ImageView img;
    ArrayList<Song> arrayList;
    public static Song songData;
    String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";
    String[] projection = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
    };
    private int i;

    public MusicFragment() {
    }

    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
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
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        DatabaseActivity databaseActivity = new DatabaseActivity(getActivity());

        ListView listView = (ListView)view.findViewById(R.id.ls);
        arrayList = new ArrayList<>();
        artistList = new ArrayList<>();

        MusicAdapter musicAdapter = new MusicAdapter(getContext(), R.id.song_layout, arrayList);

        //get all music
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            songData = new Song(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getName()).exists())
                arrayList.add(new Song(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            listView.setAdapter(musicAdapter);
                if(songData.getDuration() != null) {
                    artistList.add(songData.getDuration());
                }
        }
        if(arrayList.size()==0){
            listView.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (i = 0; i < arrayList.size(); i++) {
                    if (position == i) {
                        Intent intent = new Intent(getActivity().getBaseContext(),MusicPlayerActivity.class);
                        intent.putExtra("LIST",arrayList);
                        intent.putExtra("pos", i);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (position == i) {
                        addToPlayList addToPlayList = new addToPlayList();
                        addToPlayList.setSong_name(arrayList.get(i).getName());
                        addToPlayList.setAirtist_song(arrayList.get(i).getPath());
                        addToPlayList.setDur_song(arrayList.get(i).getDuration());
                        boolean kq = databaseActivity.InsertToPlayList(addToPlayList);
                        if(kq)  Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                        else Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }
}
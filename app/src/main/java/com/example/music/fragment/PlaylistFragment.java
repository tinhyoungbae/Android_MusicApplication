package com.example.music.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.music.DatabaseActivity;
import com.example.music.MusicAdapter;
import com.example.music.MusicPlayerActivity;
import com.example.music.PlayListAdapter;
import com.example.music.R;
import com.example.music.Singer;
import com.example.music.SingerAdapter;
import com.example.music.Song;
import com.example.music.addToPlayList;

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
    int i;
    public static ArrayList<addToPlayList> addToPlayListArrayList;

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

    public void addToPlayList(DatabaseActivity databaseActivity, ListView listView, PlayListAdapter playListAdapter){
        Cursor c = databaseActivity.ViewPlayList();
        c.moveToFirst();
        while(!c.isAfterLast()){
            int a = c.getColumnIndex("songname");
            int b = c.getColumnIndex("airtist");
            int d = c.getColumnIndex("dur");
            addToPlayListArrayList.add(new addToPlayList(c.getString(a), c.getString(b), c.getString(d)));
            c.moveToNext();
        }
        listView.setAdapter(playListAdapter);
    }

    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        addToPlayListArrayList = new ArrayList<>();
        DatabaseActivity databaseActivity = new DatabaseActivity(getContext());
        ListView listView = (ListView) view.findViewById(R.id.playlist_listview);
        PlayListAdapter playListAdapter = new PlayListAdapter(getContext(), R.id.playlist_layout, addToPlayListArrayList);

        addToPlayList(databaseActivity, listView, playListAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (i = 0; i < addToPlayListArrayList.size(); i++) {
                    if (position == i) {
                        addToPlayList addToPlayList = new addToPlayList();
                        addToPlayList.setSong_name(addToPlayListArrayList.get(i).getSong_name());
                        boolean kq = databaseActivity.DeleteFromPlayList(addToPlayList);
                        if(kq) {
                            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (i = 0; i < addToPlayListArrayList.size(); i++) {
                    if (position == i) {
                        Intent intent = new Intent(getActivity().getBaseContext(),MusicPlayerActivity.class);
                        intent.putExtra("LISTFROMPLAYLIST",addToPlayListArrayList);
                        intent.putExtra("posfromplaylist", i);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });
    }
}
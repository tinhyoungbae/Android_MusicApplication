package com.example.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.music.MusicAdapter;
import com.example.music.R;
import com.example.music.Singer;
import com.example.music.SingerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Singer> singerArrayList;

    public SingerFragment() {
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
    public static SingerFragment newInstance(String param1, String param2) {
        SingerFragment fragment = new SingerFragment();
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
        return inflater.inflate(R.layout.fragment_singer, container, false);
    }

    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        singerArrayList = new ArrayList<>();
        SingerAdapter singerAdapter = new SingerAdapter(getContext(), R.id.singer_layout, singerArrayList);
        ListView listView = (ListView) view.findViewById(R.id.airtist_listview);
        for(int i=0; i<MusicFragment.artistList.size(); i++){
            if(!singerArrayList.contains(MusicFragment.artistList.get(i))) {
                singerArrayList.add(new Singer(MusicFragment.artistList.get(i)));
            }
        }
        listView.setAdapter(singerAdapter);
    }
}
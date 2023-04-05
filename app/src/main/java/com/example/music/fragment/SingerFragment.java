package com.example.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.music.R;
import com.example.music.Singer;
import com.example.music.SingerAdapter;

import java.util.ArrayList;

public class SingerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ArrayList<Singer> singerArrayList;

    public SingerFragment() {
    }

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

        //Lấy dữ liệu từ artistList của MusicFragment đổ vào singerArrayList
        for(int i=0; i<MusicFragment.artistList.size(); i++){
            //Kiểm tra nếu dữ liệu không trùng thì đổ dữ liệu vào
            if(!singerArrayList.contains(MusicFragment.artistList.get(i))) {
                singerArrayList.add(new Singer(MusicFragment.artistList.get(i)));
            }
        }
        listView.setAdapter(singerAdapter);
    }
}
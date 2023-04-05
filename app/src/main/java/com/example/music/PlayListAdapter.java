package com.example.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//Custom Adapter cho addToPlayList
public class PlayListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<addToPlayList> playList;

    public PlayListAdapter(Context context, int layout, List<addToPlayList> playList) {
        this.context = context;
        this.layout = layout;
        this.playList = playList;
    }

    public PlayListAdapter(){
    }

    @Override
    public int getCount() {
        return playList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.playlist_item, null);

        TextView getName = view.findViewById(R.id.song_name);
        TextView getAir = view.findViewById(R.id.song_air);
        TextView getDur = view.findViewById(R.id.song_dur);

        addToPlayList addToPlayList = playList.get(i);
        getName.setText(addToPlayList.getSong_name());
        getAir.setText(addToPlayList.getAirtist_song());
        getDur.setText(addToPlayList.getDur_song());

        return view;
    }
}

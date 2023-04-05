package com.example.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//Custom Adapter cho Song
public class MusicAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Song> songList;

    public MusicAdapter(Context context, int layout, List<Song> songList) {
        this.context = context;
        this.layout = layout;
        this.songList = songList;
    }

    public MusicAdapter() {
    }

    @Override
    public int getCount() {
        return songList.size();
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
        view = inflater.inflate(R.layout.song_item, null);

        TextView getName = view.findViewById(R.id.song_name);
        TextView getDur = view.findViewById(R.id.song_dur);

        Song song = songList.get(i);
        getName.setText(song.getName());
        getDur.setText(song.getDuration());

        return view;
    }
}

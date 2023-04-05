package com.example.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Custom Adapter cho Singer
public class SingerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Singer> arrayList;

    public SingerAdapter(Context context, int layout, List<Singer> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = (ArrayList<Singer>) arrayList;
    }

    public SingerAdapter() {
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        view = inflater.inflate(R.layout.singer_item, null);

        TextView getName = view.findViewById(R.id.singer_name);

        Singer singer = (Singer) arrayList.get(i);
        getName.setText(singer.getSinger_name());

        return view;
    }
}

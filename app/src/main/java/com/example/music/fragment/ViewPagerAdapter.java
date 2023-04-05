package com.example.music.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    //Khai báo các Fragment
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new MusicFragment();
            case 1:
                return new PlaylistFragment();
            case 2:
                return new SingerFragment();
            case 3:
                return new AccountFragment();
            default:
                return new BlankFragment();
        }
    }

    //Số lượng Fragment
    public int getCount(){
        return 5;
    }
}

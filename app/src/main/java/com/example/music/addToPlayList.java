package com.example.music;

import java.io.Serializable;

//Custom cho Adapter(PlayListAdapter)
public class addToPlayList implements Serializable {
    private String song_name;
    private String airtist_song;
    private String dur_song;

    public addToPlayList(String song_name, String airtist_song, String dur_song) {
        this.song_name = song_name;
        this.airtist_song = airtist_song;
        this.dur_song = dur_song;
    }

    public addToPlayList(){

    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getAirtist_song() {
        return airtist_song;
    }

    public void setAirtist_song(String airtist_song) {
        this.airtist_song = airtist_song;
    }

    public String getDur_song() {
        return dur_song;
    }

    public void setDur_song(String dur_song) {
        this.dur_song = dur_song;
    }
}

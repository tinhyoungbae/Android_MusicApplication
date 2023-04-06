package com.example.music;

import java.io.Serializable;

//Custom cho Adapter(PlayListAdapter)
public class addToPlayList implements Serializable {
    private String song_name;
    private String url_song;
    private String singer_song;

    public addToPlayList(String song_name, String url_song, String singer_song) {
        this.song_name = song_name;
        this.url_song = url_song;
        this.singer_song = singer_song;
    }

    public addToPlayList(){

    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getUrl_song() {
        return url_song;
    }

    public void setUrl_song(String url_song) {
        this.url_song = url_song;
    }

    public String getSinger_song() {
        return singer_song;
    }

    public void setSinger_song(String singer_song) {
        this.singer_song = singer_song;
    }
}

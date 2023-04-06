package com.example.music;

import java.io.Serializable;

//Custom cho Adapter(MusicAdapter)
public class Song implements Serializable{
    private String name;
    private String path;
    private String singer;

    public Song(String name, String path, String singer) {
        this.name = name;
        this.path = path;
        this.singer = singer;
    }

    public Song(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSinger() {
        return singer;
    }

    public void setDuration(String singer) {
        this.singer = singer;
    }
}

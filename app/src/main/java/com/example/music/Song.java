package com.example.music;

import java.io.Serializable;

//Custom cho Adapter(MusicAdapter)
public class Song implements Serializable{
    private String name;
    private String path;
    private String duration;

    public Song(String name, String path, String duration) {
        this.name = name;
        this.path = path;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

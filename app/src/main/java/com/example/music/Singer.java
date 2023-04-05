package com.example.music;

//Custom cho Adapter(SingerAdapter)
public class Singer {
    private String singer_name;

    public Singer(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }
}
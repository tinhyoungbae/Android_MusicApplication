package com.example.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseActivity extends SQLiteOpenHelper {
    static String dbname = "playlist.db";
    static String pl_create = "CREATE TABLE IF NOT EXISTS Playlist (songname TEXT NOT NULL, airtist TEXT NOT NULL, dur TEXT NOT NULL)";
    static String pl_drop = "DROP TABLE IF EXISTS Playlist";
    static String TABLE_NAME = "Playlist";
    static int version = 1;
    public DatabaseActivity(Context context){
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(this.pl_create);
        }catch(Exception e){


        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(this.pl_drop);
        onCreate(sqLiteDatabase);
    }
    public boolean InsertSong(Song s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("songname", s.getName());
        cv.put("airtist", s.getPath());
        cv.put("dur", s.getDuration());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        if (result == -1)
            return false;
        else return true;
    }

    public boolean DeleteSong(Song s){
        SQLiteDatabase db = this.getWritableDatabase();

        String songname =s.getName();
        String []para = new String[1];
        para[0]=s.getName();

        long result = db.delete(TABLE_NAME,"songname=?",para);

        if (result==-1)
            return false;
        else return true;
    }
    public Cursor ViewSong(){
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        String []cols = new String[4];
        cols[0] = "songname";
        cols[1] = "airtist";
        cols[2] = "dur";
        c = db.query(TABLE_NAME, cols, null, null, null, null, null);
        return c;
    }
    }
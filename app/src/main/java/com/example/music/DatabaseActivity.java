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

    //Thêm một Object addToPlayList(Song) vào Database
    public boolean InsertToPlayList(addToPlayList addToPlayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("songname", addToPlayList.getSong_name());
        cv.put("airtist", addToPlayList.getAirtist_song());
        cv.put("dur", addToPlayList.getDur_song());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        if (result == -1)
            return false;
        else return true;
    }

    //Xóa một Object addToPlayList(Song) khỏi Database
    public boolean DeleteFromPlayList(addToPlayList addToPlayList){
        SQLiteDatabase db = this.getWritableDatabase();

        String songname =addToPlayList.getSong_name();
        String []para = new String[1];
        para[0]=addToPlayList.getSong_name();

        long result = db.delete(TABLE_NAME,"songname=?",para);

        if (result==-1)
            return false;
        else return true;
    }

    //Xem tất cả các Object addToPlayList(Song) có trong Database
    public Cursor ViewPlayList(){
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
package com.example.music;

import static com.example.music.MainActivity.CHANNEL_ID;

import android.app.Activity;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.music.fragment.PlaylistFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MusicPlayerActivity extends Activity {
    public ArrayList<Song> songsList;
    public ArrayList<addToPlayList> songsListFromPlayList;
    public static ArrayList<Song> playList;
    private int pos, posfromplaylist;
    static MediaPlayer mediaPlayer;
    TextView textView, get_min_time, get_max_time;
    ImageView pre, pause, next, img_s, get_playlist_add;
    SeekBar seekBar;
    public String s = "";

    //Phát
    public void Play(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            mediaPlayer.release();
        }
    }

    //Phát 1 bài hát từ ArrayList<Song>
    public void playSong(int i, ArrayList<Song> songsList){
        Play();
        textView.setText(songsList.get(i).getName());
        Uri uri = Uri.parse(Uri.parse(songsList.get(i).getPath()).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }

    //Phát 1 bài hát từ ArrayList<addToPlayList>
    public void playSongFromPlayList(int i, ArrayList<addToPlayList> songsListFromPlayList){
        Play();
        textView.setText(songsListFromPlayList.get(i).getSong_name());
        Uri uri = Uri.parse(Uri.parse(songsListFromPlayList.get(i).getAirtist_song()).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }

    //Lấy thời gian của 1 bài hát
    public void setTimer(){
        //Chuyển thời gian về định dạng phút : giây
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        //Set tổng thời gian của bài hát vào TextView
        get_max_time.setText(simpleDateFormat.format(mediaPlayer.getDuration()) +"");
        //Set tổng thời gian của bài hát vào Seekbar
        seekBar.setMax(mediaPlayer.getDuration());
    }

    //Gắn thời gian của 1 bài hát vào thanh Seekbar
    public void setSeekBarTimer(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Chuyển thời gian về định dạng phút : giây
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                //Set thời gian hiện tại của bài hát vào TextView
                get_min_time.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                //Set thời gian hiện tại của bài hát vào Seekbar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    //Xử lý sự kiện khi Seekbar hết thời gian
                    public void onCompletion(MediaPlayer mp) {
                        nextSong(songsList, songsListFromPlayList, s);
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    //Dừng một bài hát đang phát
    public void pauseSong(int i){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            pause.setImageResource(R.drawable.play);
        }else {
            mediaPlayer.start();
            pause.setImageResource(R.drawable.pause);
        }
    }

    //Tạo thanh báo trên thanh trạng thái
    private void sendNotificationMedia(String s, int p){
        if(songsList != null && !songsList.isEmpty()) {
            s = songsList.get(p).getDuration();
        }
        if(songsListFromPlayList != null && !songsListFromPlayList.isEmpty()){
            s = songsListFromPlayList.get(p).getDur_song();
        }
        //Ảnh nền
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.taeyang);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.music_apk_icon)
                .setSubText("Nguyễn Văn Tình")
                .setContentTitle(textView.getText().toString())
                .setContentText(s)
                .setLargeIcon(bitmap)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.back, "Previous", null) // #0
                .addAction(R.drawable.pause, "Pause", null)  // #1
                .addAction(R.drawable.next, "Next", null)     // #2
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(19999, notification);
    }

    public void nextSong(ArrayList<Song> songsList, ArrayList<addToPlayList> songsListFromPlayList, String s){
        pos++;
        posfromplaylist++;

        //Phát một bài hát tiếp theo từ MusicFragment
        if(songsList != null && !songsList.isEmpty()) {
            if(pos > songsList.size() - 1){
                pos = songsList.size()-1;
            }
            get_playlist_add.setImageResource(R.drawable.heart_empty);
            playSong(pos, songsList);
            setTimer();
            setSeekBarTimer();
            //Bật thông báo trên thanh trạng thái
            sendNotificationMedia(s, pos);
        }
        //Phát một bài hát tiếp theo từ PlayListFragment
        if(songsListFromPlayList != null && !songsListFromPlayList.isEmpty()){
            if(posfromplaylist > songsListFromPlayList.size() - 1){
                posfromplaylist = songsListFromPlayList.size()-1;
            }
            get_playlist_add.setImageResource(R.drawable.heart_empty);
            playSongFromPlayList(posfromplaylist, songsListFromPlayList);
            setTimer();
            setSeekBarTimer();
            //Bật thông báo trên thanh trạng thái
            sendNotificationMedia(s, posfromplaylist);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        DatabaseActivity databaseActivity = new DatabaseActivity(this);
        textView = findViewById(R.id.song_n);
        get_min_time = findViewById(R.id.min_time);
        get_max_time = findViewById(R.id.max_time);
        seekBar = findViewById(R.id.seekBar);
        pre = findViewById(R.id.pre);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        img_s = findViewById(R.id.img_song);
        get_playlist_add = findViewById(R.id.playlist_add);
        playList = new ArrayList<>();
        Animation animation;
        MusicAdapter playListAdapter = new MusicAdapter(MusicPlayerActivity.this, R.id.song_layout, playList);

        //Hiệu ứng xoay ảnh
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_img);
        //img_s.startAnimation(animation);

        //Nhận dữ liệu từ MusicFragment gửi thông qua Intent
        songsList = (ArrayList<Song>) getIntent().getSerializableExtra("LIST");
        pos = getIntent().getIntExtra("pos", 0);

        //Nhận dữ liệu từ PlayListFragment gửi thông qua Intent
        songsListFromPlayList = (ArrayList<addToPlayList>) getIntent().getSerializableExtra("LISTFROMPLAYLIST");
        posfromplaylist = getIntent().getIntExtra("posfromplaylist", 0);

        //Phát một bài hát từ MusicFagment gửi qua
        if(songsList != null && !songsList.isEmpty()){
            get_playlist_add.setImageResource(R.drawable.heart_empty);
            playSong(pos, songsList);
            sendNotificationMedia(s, pos);
            setTimer();
            setSeekBarTimer();
            //Thêm bài hát đang phát vào PlayList(Playlist Database)
            get_playlist_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(songsList != null && !songsList.isEmpty()){
                        get_playlist_add.setImageResource(R.drawable.heart_full);
                        addToPlayList addToPlayList = new addToPlayList();
                        addToPlayList.setSong_name(songsList.get(pos).getName());
                        addToPlayList.setAirtist_song(songsList.get(pos).getPath());
                        addToPlayList.setDur_song(songsList.get(pos).getDuration());
                        boolean kq = databaseActivity.InsertToPlayList(addToPlayList);
                        if(kq)  Toast.makeText(MusicPlayerActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        else Toast.makeText(MusicPlayerActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        //Phát một bài hát từ PlayListFagment gửi qua
        if(songsListFromPlayList != null && !songsListFromPlayList.isEmpty()){
            get_playlist_add.setImageResource(R.drawable.heart_empty);
            playSongFromPlayList(posfromplaylist, songsListFromPlayList);
            get_playlist_add.setImageResource(R.drawable.heart_full);
            sendNotificationMedia(s, posfromplaylist);
            setTimer();
            setSeekBarTimer();
            //Xóa bài hát đang phát khỏi PlayList(Playlist Database)
            get_playlist_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    get_playlist_add.setImageResource(R.drawable.heart_empty);
                    addToPlayList addToPlayList = new addToPlayList();
                    addToPlayList.setSong_name(PlaylistFragment.addToPlayListArrayList.get(posfromplaylist).getSong_name());
                    boolean kq = databaseActivity.DeleteFromPlayList(addToPlayList);
                    if(kq) {
                        Toast.makeText(MusicPlayerActivity.this, "Đã xóa khỏi playlist", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(MusicPlayerActivity.this, "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
            });
        }

        //Xử lý sự kiện kéo thời gian trên Seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        pause.setImageResource(R.drawable.pause);

        //Dừng một bài hát/ tiếp tục phát
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pause.setImageResource(R.drawable.play);
                }else {
                    mediaPlayer.start();
                    pause.setImageResource(R.drawable.pause);
                }
            }
        });

        //Phát một bài hát trước đó
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos--;
                posfromplaylist--;
                if(pos <= 0){
                    pos = 0;
                    posfromplaylist = 0;
                }
                if(posfromplaylist <= 0){
                    posfromplaylist = 0;
                }
                //Phát một bài hát trước đó từ MusicFragment
                if(songsList != null && !songsList.isEmpty()) {
                    get_playlist_add.setImageResource(R.drawable.heart_empty);
                    playSong(pos, songsList);
                    //Bật thông báo trên thanh trạng thái
                    sendNotificationMedia(s, pos);
                }
                //Phát một bài hát trước đó từ PlayListFragment
                if(songsListFromPlayList != null && !songsListFromPlayList.isEmpty()){
                    get_playlist_add.setImageResource(R.drawable.heart_empty);
                    playSongFromPlayList(posfromplaylist, songsListFromPlayList);
                    //Bật thông báo trên thanh trạng thái
                    sendNotificationMedia(s, posfromplaylist);
                }
                //Set thời gian trên Seekbar và kéo trên thanh thời gian
                setTimer();
                setSeekBarTimer();
            }
        });

        //Phát 1 bài hát tiếp theo
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong(songsList, songsListFromPlayList, s);
                //Set thời gian trên Seekbar và kéo trên thanh thời gian
                setTimer();
                setSeekBarTimer();
            }
        });
    }
}
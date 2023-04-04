package com.example.music;

import static com.example.music.MainActivity.CHANNEL_ID;
import static com.example.music.fragment.MusicFragment.songData;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.music.fragment.MusicFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerActivity extends Activity {
    public ArrayList<Song> songsList;
    public static ArrayList<Song> playList;
    private int pos;
    static MediaPlayer mediaPlayer;
    TextView textView, get_min_time, get_max_time;
    ImageView pre, pause, next, img_s, get_playlist_add;
    SeekBar seekBar;

    public void playSong(int i){
        if(mediaPlayer != null){
            mediaPlayer.start();
            mediaPlayer.release();
        }
        textView.setText(songsList.get(i).getName());
        Uri uri = Uri.parse(Uri.parse(songsList.get(i).getPath()).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }

    public void setTimer(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        get_max_time.setText(simpleDateFormat.format(mediaPlayer.getDuration()) +"");
        seekBar.setMax(mediaPlayer.getDuration());
    }

    public void setSeekBarTimer(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("mm:ss");
                get_min_time.setText(simpleDateFormat1.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playSong(pos+1);
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public void pauseSong(int i){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            pause.setImageResource(R.drawable.play);
        }else {
            mediaPlayer.start();
            pause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

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
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_img);

        MusicAdapter playListAdapter = new MusicAdapter(MusicPlayerActivity.this, R.id.song_layout, playList);

        songsList = (ArrayList<Song>) getIntent().getSerializableExtra("LIST");
        pos = getIntent().getIntExtra("pos", 0);

        playSong(pos);
        sendNotificationMedia();
        setTimer();
        setSeekBarTimer();
        //img_s.startAnimation(animation);
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

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos--;
                if(pos < 0){
                    pos = 0;
                }
                playSong(pos);
                setTimer();
                setSeekBarTimer();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos++;
                if(pos > songsList.size() - 1){
                    pos = songsList.size();
                }
                playSong(pos);
                setTimer();
                setSeekBarTimer();
            }
        });

        get_playlist_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playList.add(new Song(songsList.get(0).toString(), songsList.get(1).toString(), songsList.get(2).toString()));
                get_playlist_add.setImageResource(R.drawable.heart_full);
            }
        });
    }

    private void sendNotificationMedia(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.taeyang);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.music_apk_icon)
                .setSubText("Nguyễn Văn Tình")
                .setContentTitle(textView.getText().toString())
                .setContentText(songsList.get(pos).getDuration())
                .setLargeIcon(bitmap)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.back, "Previous", null) // #0
                .addAction(R.drawable.pause, "Pause", null)  // #1
                .addAction(R.drawable.next, "Next", null)     // #2
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(19999, notification);

    }
}
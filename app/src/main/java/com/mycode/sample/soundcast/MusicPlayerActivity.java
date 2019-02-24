package com.mycode.sample.soundcast;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {
    Button pauseSongButton,nextSongButton,previousSongButton;
    SeekBar seekBarSong;
    ImageView displaySongThumbnail;
    TextView displaySongTitle;
    public static  MediaPlayer mediaPlayer;
    public  static String getDownloadedMusicPath;
    String songTitle;
    int position;
    String getMusic,getThumbnail;
    String results;
    public ArrayList<File> sogArrayList;
    Thread seekBarUpdate;
    File[] files;
    String songName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        pauseSongButton =(Button)findViewById(R.id.pauseSongButton);
        nextSongButton =(Button)findViewById(R.id.nextSongButton);
        previousSongButton =(Button)findViewById(R.id.previousSongButton);
        seekBarSong =(SeekBar) findViewById(R.id.seekBarSong);
        displaySongThumbnail =(ImageView)findViewById(R.id.displaySongThumbnail);
        displaySongTitle = (TextView)findViewById(R.id.displaySongTitle);
        mediaPlayer = new MediaPlayer();
        getSupportActionBar().setTitle("Now PLaying");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String path = Environment.getExternalStorageDirectory().toString()+"/SoundCast/Talview/Audio";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        files  = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        sogArrayList = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getAbsolutePath());
            sogArrayList.add(new File(files[i].getAbsolutePath()));
        }
        Log.d("LLLLKKKKK",""+sogArrayList);
        songName = sogArrayList.get(position).getAbsolutePath();
        seekBarUpdate = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBarSong.setProgress(currentPosition);
                    }catch (Exception ex){

                        Log.d("","");
                    }
                }
            }
        };
        Intent intent = getIntent();
        if (intent != null){
            getDownloadedMusicPath = intent.getStringExtra("songToPlay");
            songTitle = intent.getStringExtra("songTitle");
            Log.d("TAG...........",""+getDownloadedMusicPath);
        }
        File fileImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/SoundCast/Talview/Image/"+songTitle+".jpg");

        if (songTitle != null && displaySongThumbnail != null){
            displaySongTitle.setText(songTitle);
            Picasso.with(getApplicationContext()).load(fileImage).into(displaySongThumbnail);
        }
        try {
            if (getDownloadedMusicPath != null){
                mediaPlayer.setDataSource(getDownloadedMusicPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
                seekBarSong.setMax(mediaPlayer.getDuration());
            }else{
                pauseSongButton.setBackgroundResource(R.drawable.play_song_icon);
                Toast.makeText(this, "Music Not Available In The Media Folder.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekBarUpdate.start();
        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        pauseSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarSong.setMax(mediaPlayer.getDuration());
                if (mediaPlayer.isPlaying()){
                    pauseSongButton.setBackgroundResource(R.drawable.play_song_icon);
                    mediaPlayer.pause();
                }else {
                    pauseSongButton.setBackgroundResource(R.drawable.pause_song_icon);
                    mediaPlayer.start();

                }
            }
        });
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%sogArrayList.size());
                Uri uri = Uri.parse(sogArrayList.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                if (!mediaPlayer.isPlaying()){
                    seekBarSong.setMax(mediaPlayer.getDuration());
                    pauseSongButton.setBackgroundResource(R.drawable.pause_song_icon);
                }
                songName = sogArrayList.get(position).getName();
                File fileImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SoundCast/Talview/Image/"+songName.replace(".mp3","")+".jpg");
                displaySongTitle.setText(songName);
                Log.d("MusicAppTag....",""+fileImage);
                Picasso.with(getApplicationContext()).load(fileImage).into(displaySongThumbnail);
                mediaPlayer.start();
            }
        });
        previousSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(sogArrayList.size()-1):(position-1);
                Uri uri = Uri.parse(sogArrayList.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                if (!mediaPlayer.isPlaying()){
                    seekBarSong.setMax(mediaPlayer.getDuration());
                    pauseSongButton.setBackgroundResource(R.drawable.pause_song_icon);
                }
                songName = sogArrayList.get(position).getName();
                File fileImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SoundCast/Talview/Image/"+songName.replace(".mp3","")+".jpg");
                displaySongTitle.setText(songName);
                Log.d("MusicAppTag....",""+fileImage);
                Picasso.with(getApplicationContext()).load(fileImage).into(displaySongThumbnail);
                mediaPlayer.start();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               super.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.mycode.sample.soundcast;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MediaPlayerService extends Service {
    //private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri uri = Uri.parse("https://static.talview.com/hiring/android/soundcast/mp3/fast-and-furious.mp3");
        mPlayer = MediaPlayer.create(this, uri);
        // mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }
        mPlayer.start();
        return START_STICKY;
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic() {
        if (mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mPlayer.stop();
    }

//    public boolean onError(MediaPlayer mp, int what, int extra) {
//
//        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
//        if (mPlayer != null) {
//            try {
//                mPlayer.stop();
//                mPlayer.release();
//            } finally {
//                mPlayer = null;
//            }
//        }
//        return false;
//    }
}

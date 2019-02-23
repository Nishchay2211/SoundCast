package com.mycode.sample.soundcast;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mycode.sample.soundcast.Adapter.SongAdapter;
import com.mycode.sample.soundcast.Utils.ApiUtils;
import com.mycode.sample.soundcast.model.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mycode.sample.soundcast.MusicPlayerActivity.mediaPlayer;

public class MainActivity extends Activity {
    SongList songList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<Result> results = new ArrayList<Result>();
    RecyclerView recycle_Song;
    SongAdapter songAdapter;
    MusicPlayerActivity musicPlayerActivity = new MusicPlayerActivity();
    //FloatingActionButton checkDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);
        recycle_Song = (RecyclerView)findViewById(R.id.recycle_Song);
        //checkDownload = (FloatingActionButton)findViewById(R.id.checkDownload);
        songList = ApiUtils.getSongService();
        recycle_Song.setHasFixedSize(true);
        recycle_Song.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fetchData();
        //startService(new Intent(this,MediaPlayerService.class));
    }
    private void fetchData() {
        compositeDisposable.add(songList.getSongDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetSongDetails>() {
                    @Override
                    public void accept(final GetSongDetails getSongDetails) throws Exception {
                        List<Result> tempResults = getSongDetails.getResults();
                        if (tempResults!= null){
                            for (Result tempResult : tempResults){
                                if (tempResult.getTitle()!= null && tempResult.getLink().contains(".mp3")){
                                    results.add(tempResult);
                                }
                            }
                        }
                        songAdapter = new SongAdapter(MainActivity.this,results);
                        recycle_Song.setAdapter(songAdapter);
                    }
                }));
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getApplicationContext(), "Permission was denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 101)
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (songAdapter != null)
        songAdapter.notifyDataSetChanged();

    }
}

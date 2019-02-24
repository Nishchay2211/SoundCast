package com.mycode.sample.soundcast;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends Activity {
    ImageView splashImageView;
    TextView  splashTextView;
    AlertDialog.Builder alertDialog;
    AlertDialog alertBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImageView = (ImageView)findViewById(R.id.splashImageView);
        splashTextView = (TextView)findViewById(R.id. splashTextView );
        if (!isNetworkAvailable()) {
            try{
                alertDialog = new AlertDialog.Builder(SplashActivity.this);
                alertDialog.setTitle(" No Internet Connection!! ");
                alertDialog.setMessage("Check Your Internet Connection.");
                alertDialog.setIcon(R.drawable.musicicon);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
                if (!SplashActivity.this.isFinishing()){
                    alertBox = alertDialog.show();
                    alertBox.setCancelable(false);
                    alertBox.setCanceledOnTouchOutside(false);
                }

            }catch (Exception ex){
                Log.d("TAG","Internet Issue"+ex);
            }

        }else {
            Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.myanim);
            splashImageView.startAnimation(animation);
            Animation animationText = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.textanim);
            splashTextView.startAnimation(animationText);
            final Intent intent = new Intent(this,MainActivity.class);
            Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(5000);
                    }catch (Exception ex){

                    }
                    finally {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }

    }
    private  boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}

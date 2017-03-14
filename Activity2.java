package com.vogelplay.vogel3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {


    FrameLayout frameLayout2;
    TextView tap;
    ImageView bird;
    int bird_x, bird_y, height, width;
    FrameLayout.LayoutParams flp_bird;
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Creating a new FrameLayout
        frameLayout2 = new FrameLayout(this);
        frameLayout2.setBackgroundColor(Color.argb(255,153,204,255));
        setContentView(frameLayout2);
        super.onCreate(savedInstanceState);
         width = this.getResources().getDisplayMetrics().widthPixels;
         height = this.getResources().getDisplayMetrics().heightPixels;
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                500,100);
        flpTtoS.setMargins(width/2 - 250,height/2 -50,0,0);
        // Creating a new TextView
        tap = new TextView(this);
        tap.setText("Tap To Start");
        tap.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        tap.setGravity(Gravity.CENTER);
        tap.setTextColor(Color.YELLOW);
        tap.setTextSize(25);
        tap.setLayoutParams(flpTtoS);
        frameLayout2.addView(tap);
    //wave
        addWave();
        //Sun
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                200,200);
        flpSun.setMargins(width - 400 , 100,0,0);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.sun);
        image.setLayoutParams(flpSun);
        frameLayout2.addView(image);
        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -358f, 100,100);
        rotate_sun.setDuration(10000);
        rotate_sun.setRepeatCount(-1);
        rotate_sun.setRepeatMode(1);
        image.startAnimation(rotate_sun);



        //bird
        flp_bird = new FrameLayout.LayoutParams(
                200,200);
        bird_x = width/3; bird_y= height/2;
        flp_bird.setMargins(bird_x , bird_y,width/3 + 200,height/2 + 200);
        bird = new ImageView(this);
        bird.setImageResource(R.drawable.bird1);
        bird.setLayoutParams(flp_bird);
        frameLayout2.addView(bird);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        frameLayout2.removeView(tap);
        //     frameLayout2.setBackgroundColor(Color.DKGRAY);
//        Intent i = new Intent(Activity2.this,Activity3.class);
//        startActivity(i);


        switch (event.getAction()) {
            case MotionEvent.ACTION_BUTTON_PRESS: {
                Log.i("ksd","kksfawefekkkk");
                break;
            }
            case MotionEvent.ACTION_BUTTON_RELEASE: {
                flp_bird.setMargins(bird_x +10, bird_y -20,0,0);
                Log.i("k","kkk");
                break;
            }case MotionEvent.ACTION_CANCEL:{
                flp_bird.setMargins(bird_x , bird_y + 20,0,0);
                Log.i("ksdf","kxdfsdfkk");
            }

        }

        bird.setLayoutParams(flp_bird);
        return super.onTouchEvent(event);

    }
    public void addWave() {
        //Wave
        for (int i =0 ; i<7; i++){
            ImageView waveStatic = new ImageView(this);
            waveStatic.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpwaveStatic = new FrameLayout.LayoutParams(
                    400, 200);
            flpwaveStatic.setMargins(0 + (390*i) -5, height - 170, 0, 0);
            waveStatic.setLayoutParams(flpwaveStatic);
            frameLayout2.addView(waveStatic);

            ImageView wave = new ImageView(this);
            wave.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
                    400, 200);
            flpWave.setMargins((390*i)-400, height - 170, 0, 0);
            wave.setLayoutParams(flpWave);
            TranslateAnimation move_wave = new TranslateAnimation(0, 400, 0, 0);
            move_wave.setDuration(500);
            move_wave.setRepeatCount(-1);
            move_wave.setRepeatMode(1);
            move_wave.setFillBefore(true);
            wave.startAnimation(move_wave);
            frameLayout2.addView(wave);
            ImageView wave2 = new ImageView(this);
            wave2.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
                    400, 200);
            flpWave.setMargins((390*i)-600, height - 170, 0, 0);
            wave2.setLayoutParams(flpWave2);
            TranslateAnimation move_wave2 = new TranslateAnimation(0, 400, 0, 0);
            move_wave2.setDuration(700);
            move_wave2.setRepeatCount(-1);
            move_wave2.setRepeatMode(1);
            wave2.startAnimation(move_wave2);
            frameLayout2.addView(wave2);
        }
//        ImageView wave = new ImageView(this);
//        wave.setImageResource(R.drawable.wave2);
//        FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
//                400, 200);
//        flpWave.setMargins(0 - 390, height - 170, 0, 0);
//        wave.setLayoutParams(flpWave);
//        TranslateAnimation move_wave = new TranslateAnimation(0, 380, 0, 0);
//        move_wave.setDuration(1800);
//        move_wave.setRepeatCount(-1);
//        move_wave.setRepeatMode(1);
//        wave.startAnimation(move_wave);
//        frameLayout2.addView(wave);
//        ImageView wave2 = new ImageView(this);
//        wave2.setImageResource(R.drawable.wave2);
//        FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
//                400, 200);
//        flpWave2.setMargins(0, height - 170, 0, 0);
//        wave2.setLayoutParams(flpWave2);
//        TranslateAnimation move_wave2 = new TranslateAnimation(0, 380, 0, 0);
//        move_wave2.setDuration(1800);
//        move_wave2.setRepeatCount(-1);
//        move_wave2.setRepeatMode(1);
//        wave2.startAnimation(move_wave2);
//        frameLayout2.addView(wave2);
//        ImageView wave3 = new ImageView(this);
//        wave3.setImageResource(R.drawable.wave2);
//        FrameLayout.LayoutParams flpWave3 = new FrameLayout.LayoutParams(
//                400, 200);
//        flpWave3.setMargins(390, height - 170, 0, 0);
//        wave3.setLayoutParams(flpWave3);
//        TranslateAnimation move_wave3 = new TranslateAnimation(0, 380, 0, 0);
//        move_wave3.setDuration(1800);
//        move_wave3.setRepeatCount(-1);
//        move_wave3.setRepeatMode(1);
//        wave3.startAnimation(move_wave3);
//        frameLayout2.addView(wave3);
//        ImageView wave4 = new ImageView(this);
//        wave4.setImageResource(R.drawable.wave2);
//        FrameLayout.LayoutParams flpWave4 = new FrameLayout.LayoutParams(
//                400, 200);
//        flpWave4.setMargins(780, height - 170, 0, 0);
//        wave4.setLayoutParams(flpWave4);
//        TranslateAnimation move_wave4 = new TranslateAnimation(0, 380, 0, 0);
//        move_wave4.setDuration(1800);
//        move_wave4.setRepeatCount(-1);
//        move_wave4.setRepeatMode(1);
//        wave4.startAnimation(move_wave4);
//        frameLayout2.addView(wave4);
//        ImageView wave5 = new ImageView(this);
//        wave5.setImageResource(R.drawable.wave2);
//        FrameLayout.LayoutParams flpWave5 = new FrameLayout.LayoutParams(
//                400, 200);
//        flpWave5.setMargins(1170, height - 170, 0, 0);
//        wave5.setLayoutParams(flpWave5);
//        TranslateAnimation move_wave5 = new TranslateAnimation(0, 380, 0, 0);
//        move_wave5.setDuration(1800);
//        move_wave5.setRepeatCount(-1);
//        move_wave5.setRepeatMode(1);
//        wave5.startAnimation(move_wave5);
//        frameLayout2.addView(wave5);


    }




}

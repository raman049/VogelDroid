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
    TextView tap, high_score;
    ImageView bird;
    int bird_x, bird_y, height, width, y_motion, score;
    boolean gameOver, started;
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
                1000,200);
        flpTtoS.setMargins(width/2 - 550,height/2 -200,0,0);
        // Creating a new TextView
        tap = new TextView(this);
        tap.setText("Tap To Start");
        tap.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        tap.setGravity(Gravity.CENTER);
        tap.setTextColor(Color.YELLOW);
        tap.setTextSize(50);
        tap.setBackgroundColor(Color.GRAY);
        tap.setLayoutParams(flpTtoS);
        frameLayout2.addView(tap);
        //High Score
        FrameLayout.LayoutParams frame_hs = new FrameLayout.LayoutParams(
                1000,100);
        frame_hs.setMargins(width/2 - 500,height/6,0,0);
        high_score = new TextView(this);
        high_score.setText("High Score: 0000000000");
        high_score.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        high_score.setGravity(Gravity.CENTER);
        high_score.setTextSize(18);
        high_score.setTextColor(Color.RED);
        high_score.setBackgroundColor(Color.GRAY);
        high_score.setLayoutParams(frame_hs);
        frameLayout2.addView(high_score);
    //wave
        addWave();
        //Sun
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                100,100);
        flpSun.setMargins(width - 400 , 100,0,0);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.sun);
        image.setLayoutParams(flpSun);
        frameLayout2.addView(image);
        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -360f, 50,50);
        rotate_sun.setDuration(10000);
        rotate_sun.setRepeatCount(-1);
        rotate_sun.setRepeatMode(1);
        image.startAnimation(rotate_sun);



        //bird
        flp_bird = new FrameLayout.LayoutParams(
                150,150);
        bird_x = width/3; bird_y= height/2;
        flp_bird.setMargins(bird_x , bird_y,width/3 + 200,height/2 + 200);
        bird = new ImageView(this);
        bird.setImageResource(R.drawable.bird1);
        bird.setLayoutParams(flp_bird);

        frameLayout2.addView(bird);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(started == true) {
            frameLayout2.removeView(tap);
            high_score.setTextSize(12);
            high_score.setTextColor(Color.BLUE);
            high_score.setX(0-250);
            high_score.setY(20);
            //Your Score
            FrameLayout.LayoutParams frame_score = new FrameLayout.LayoutParams(
                    1000,100);
            framframe_scoree_hs.setMargins(width/2 - 500,height/6,0,0);
            your_score = new TextView(this);
            your_score.setText("High Score: 0000000000");
            your_score.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
            your_score.setGravity(Gravity.CENTER);
            your_score.setTextSize(18);
            your_score.setTextColor(Color.RED);
            your_score.setBackgroundColor(Color.GRAY);
            your_score.setLayoutParams(frame_score);
            frameLayout2.addView(your_score);
        }

        bird.setY(bird.getY() + y_motion);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                fly();

                break;
            case MotionEvent.ACTION_BUTTON_RELEASE:
                bird.setX(bird.getX());
                bird.setY(bird.getY() +105);

                break;
        }

        return super.onTouchEvent(event);

    }
    public void addWave() {
        //Wave
        for (int i =0 ; i<7; i++){
            ImageView waveStatic = new ImageView(this);
            waveStatic.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpwaveStatic = new FrameLayout.LayoutParams(
                    400, 200);
            flpwaveStatic.setMargins(0 + (400*i) -5, height - 170, 0, 0);
            waveStatic.setLayoutParams(flpwaveStatic);
            frameLayout2.addView(waveStatic);

            ImageView wave = new ImageView(this);
            wave.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
                    400, 200);
            flpWave.setMargins((390*i)-400, height - 170, 0, 0);
            wave.setLayoutParams(flpWave);
            TranslateAnimation move_wave = new TranslateAnimation(0, 600, 0, 0);
            move_wave.setDuration(1000);
            move_wave.setRepeatCount(-1);
            move_wave.setRepeatMode(1);
            move_wave.setFillBefore(true);
            wave.startAnimation(move_wave);
            frameLayout2.addView(wave);

            ImageView wave2 = new ImageView(this);
            wave2.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
                    400, 200);
            flpWave2.setMargins((400*i)-605, height - 170, 0, 0);
            wave2.setLayoutParams(flpWave2);
            TranslateAnimation move_wave2 = new TranslateAnimation(0, 600, 0, 0);
            move_wave2.setDuration(800);
            move_wave2.setRepeatCount(-1);
            move_wave2.setRepeatMode(1);
            move_wave2.getFillAfter();
            wave2.startAnimation(move_wave2);
            frameLayout2.addView(wave2);
        }

    }

    public void fly(){
        if(started != true){
            started = true;
        }

            y_motion -= 5;
        }






}

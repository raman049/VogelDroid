package com.vogelplay.vogel3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {


    FrameLayout frameLayout2;
    TextView tap, high_score,your_score;
    ImageView bird;
    int bird_x, bird_y, height, width, y_motion, score;
    boolean gameOver, started,addStuff;
    FrameLayout.LayoutParams flp_bird;
    Thread thread, thread2;
    Handler handler, handler2;

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
                1000,120);
        frame_hs.setMargins(width/2 - 500,height/6,0,0);
        high_score = new TextView(this);
        high_score.setText("High Score: \n0000000000");
        high_score.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        high_score.setGravity(Gravity.CENTER);
        high_score.setTextSize(18);
        high_score.setTextColor(Color.RED);
       // high_score.setBackgroundColor(Color.GRAY);
        high_score.setLayoutParams(frame_hs);
        frameLayout2.addView(high_score);
    //wave
        addWave();
        //Sun
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                100,100);
        flpSun.setMargins(width - 300 , 100,0,0);
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
        bird.setBackgroundColor(Color.BLACK);
        frameLayout2.addView(bird);

        thread = new Thread(new MyThread());
        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (addStuff != true) {
                    addJetB();
                    addShip();
                    addCloud1();
                    addCloud2();
                    addCloud3();
                    addJetY();
                    addStuff = true;
                }
            }
        };


        //bird gravity motion thread2
        thread2 = new Thread(new MyThread2());
        thread2.start();
        handler2 = new Handler() {
            @Override
            public void handleMessage(Message message2) {
                 gravity();
                //y_motion = 2 ;
            }
        };

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(started == true) {
            frameLayout2.removeView(tap);
            high_score.setTextSize(12);
            high_score.setTextColor(Color.BLUE);
            high_score.setX(10);
            high_score.setGravity(Gravity.LEFT);
            high_score.setY(20);
            //Your Score
            FrameLayout.LayoutParams frame_score = new FrameLayout.LayoutParams(
                    1000,100);
            frame_score.setMargins(10 ,100,0,0);
            your_score = new TextView(this);
            your_score.setText("Score: \n 0000000000");
            your_score.setTypeface(Typeface.create("Droid-Sans-Mono", Typeface.NORMAL));
            your_score.setTextSize(14);
            your_score.setGravity(Gravity.LEFT);
            your_score.setTextColor(Color.RED);
            your_score.setLayoutParams(frame_score);
            frameLayout2.addView(your_score);
            boolean run_forever = true;


        }

            

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                fly();
                bird.setY(bird.getY() + y_motion);
                bird.setImageResource(R.drawable.bird2);
                break;
            case MotionEvent.ACTION_CANCEL:
              // fly();
               // bird.setImageResource(R.drawable.bird1);

                break;
        }
        bird.setImageResource(R.drawable.bird1);
        return super.onTouchEvent(event);

    }

    class MyThread implements Runnable{
        @Override
        public void run() {
            for(int i =0; i<1000;i++){
                Message message = Message.obtain();
                handler.sendMessage(message);
                try {
                    Thread.sleep(5000);
                    addStuff = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 100){
                    i =2;
                }
            }
        }
    }
    class MyThread2 implements Runnable{
        @Override
        public void run() {
            for(int i =0; i<10000;i++){
                Message message2 = Message.obtain();
                handler2.sendMessage(message2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    ImageView jetY,cloud1,cloud2,cloud3,jetB,ship;
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
    public void addJetY(){
         jetY = new ImageView(this);
        jetY.setImageResource(R.drawable.plane2);
        Random random1 = new Random();
        Random random12 = new Random();
        int random11 = 100 + random1.nextInt(height-500);
        int random112 = width + random12.nextInt(width/2);
        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
                200, 200);
        fl_jet.setMargins(random112, random11, 0, 0);
        jetY.setLayoutParams(fl_jet);
        TranslateAnimation move_jetY = new TranslateAnimation(0, 0-(width*2)-300, 0, 0);
        move_jetY.setDuration(4000);
        move_jetY.setRepeatCount(0);
        move_jetY.setRepeatMode(1);
        jetY.setBackgroundColor(Color.BLACK);
        jetY.startAnimation(move_jetY);
        frameLayout2.addView(jetY);
    }
    public void addJetB(){
        jetB = new ImageView(this);
        jetB.setImageResource(R.drawable.plane1);
        Random random1y = new Random();
        Random random1x = new Random();
        int random11y = 120 + random1y.nextInt(height -500);
        int random11x = width + random1x.nextInt(600);
        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
                200, 200);
        fl_jet.setMargins(random11x, random11y, 0, 0);
        jetB.setLayoutParams(fl_jet);
        TranslateAnimation move_jetY = new TranslateAnimation(0, 0-width*2, 0, 0);
        move_jetY.setDuration(5000);
        move_jetY.setRepeatCount(0);
        move_jetY.setRepeatMode(1);
        jetB.setBackgroundColor(Color.BLACK);
        jetB.startAnimation(move_jetY);
        frameLayout2.addView(jetB);
    }
    public void addCloud1(){
         cloud1 = new ImageView(this);
        cloud1.setImageResource(R.drawable.cloud1);
        Random random1y = new Random();
        Random random1x = new Random();
        int random11x = width + random1x.nextInt(width);
        int random11y = random1y.nextInt(100-10) + 10;
        FrameLayout.LayoutParams fl_cloud = new FrameLayout.LayoutParams(
                200, 200);
        fl_cloud.setMargins(random11x, random11y, 0, 0);
        cloud1.setLayoutParams(fl_cloud);
        TranslateAnimation move_cloud = new TranslateAnimation(0, 0-width*2 + 300, 0, 0);
        move_cloud.setDuration(5000);
        move_cloud.setRepeatCount(0);
        move_cloud.setRepeatMode(1);
        cloud1.startAnimation(move_cloud);
        frameLayout2.addView(cloud1);
    }
    public void addCloud2(){
        cloud2 = new ImageView(this);
        cloud2.setImageResource(R.drawable.cloud2);
        Random random1y = new Random();
        Random random1x = new Random();
        int random11x = width + random1x.nextInt(width);
        int random11y = random1y.nextInt(100-10) + 10;
        FrameLayout.LayoutParams fl_cloud2 = new FrameLayout.LayoutParams(
                200, 200);
        fl_cloud2.setMargins(random11x, random11y, 0, 0);
        cloud2.setLayoutParams(fl_cloud2);
        TranslateAnimation move_cloud2 = new TranslateAnimation(0, 0-width*2 + 300, 0, 0);
        move_cloud2.setDuration(5000);
        move_cloud2.setRepeatCount(0);
        move_cloud2.setRepeatMode(1);
        cloud2.startAnimation(move_cloud2);
        frameLayout2.addView(cloud2);
    }
    public void addCloud3(){
        cloud3 = new ImageView(this);
        cloud3.setImageResource(R.drawable.cloud3);
        Random random1y = new Random();
        Random random1x = new Random();
        int random11x = width + random1x.nextInt(width);
        int random11y = random1y.nextInt(100-10) + 10;
        FrameLayout.LayoutParams fl_cloud3 = new FrameLayout.LayoutParams(
                200, 200);
        fl_cloud3.setMargins(random11x, random11y, 0, 0);
        cloud3.setLayoutParams(fl_cloud3);
        TranslateAnimation move_cloud3 = new TranslateAnimation(0, 0-width*2 + 300, 0, 0);
        move_cloud3.setDuration(5000);
        move_cloud3.setRepeatCount(0);
        move_cloud3.setRepeatMode(1);
        cloud3.startAnimation(move_cloud3);
        frameLayout2.addView(cloud3);
    }
    public void addShip(){
         ship = new ImageView(this);
        ship.setImageResource(R.drawable.ship2);
        Random random1 = new Random();
        int ramdom11 = height-290 + random1.nextInt(10);
        FrameLayout.LayoutParams fl_ship = new FrameLayout.LayoutParams(
                200, 200);
        fl_ship.setMargins(0 - 200, ramdom11, 0, 0);
        ship.setLayoutParams(fl_ship);
        TranslateAnimation move_ship = new TranslateAnimation(0, width+600, 0, 0);
        move_ship.setDuration(5000);
        move_ship.setRepeatCount(0);
        move_ship.setRepeatMode(1);
        ship.startAnimation(move_ship);
        frameLayout2.addView(ship);
    }
    public void fly(){
        if(started != true){
            started = true;
        }

            y_motion = -40;

        }
    public void gravity(){
        if(started == true) {
            bird.setY(bird.getY() + 7);
        }
    }





}

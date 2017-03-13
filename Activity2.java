package com.vogelplay.vogel;

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
    int bird_x, bird_y;
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
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
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

    //Sun
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                200,200);
        flpSun.setMargins(width - 400 , 100,0,0);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.sun);
        image.setLayoutParams(flpSun);
        frameLayout2.addView(image);
        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -358f, 100,100);
        rotate_sun.setDuration(1800);
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

   //     frameLayout2.setBackgroundColor(Color.DKGRAY);
//        Intent i = new Intent(Activity2.this,Activity3.class);
//        startActivity(i);

        frameLayout2.removeView(tap);


        switch (event.getAction()) {
            case MotionEvent.ACTION_BUTTON_PRESS: {
                Log.d("ksd","kksfawefekkkk");
                break;
            }
            case MotionEvent.ACTION_BUTTON_RELEASE: {
                flp_bird.setMargins(bird_x +10, bird_y -20,0,0);
                Log.d("k","kkk");
                break;
            }case MotionEvent.ACTION_CANCEL:{
                flp_bird.setMargins(bird_x , bird_y + 20,0,0);
                Log.d("ksdf","kxdfsdfkk");
            }

        }

        bird.setLayoutParams(flp_bird);
        return super.onTouchEvent(event);




    }


}

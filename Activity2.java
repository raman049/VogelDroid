package com.vogelplay.vogel;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity{


    FrameLayout frameLayout2;
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
        TextView tap = new TextView(this);
        tap.setText("Tap To Start");
        tap.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        tap.setGravity(Gravity.CENTER);
        tap.setTextColor(Color.YELLOW);
        tap.setTextSize(25);
        tap.setLayoutParams(flpTtoS);
        frameLayout2.addView(tap);


//        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
//                200,200);
//        flpTtoS.setMargins(width/2 ,height/2+100,0,0);



    }


}

package com.vogelplay.vogel;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_main);
        // Creating a new RelativeLayout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        FrameLayout frameLayout = new FrameLayout(this);
        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT ,
                FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout.setBackgroundColor(Color.RED);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // Setting the RelativeLayout as our content view
        setContentView(frameLayout);
        // Creating a new TextView
        TextView tv = new TextView(this);
        tv.setText("VOGEL");
        tv.setTextColor(Color.BLUE);
        tv.setPadding(flp.width/2, flp.height/8, 500, 150);
        tv.setTextSize(100);
        //Typeface face= Typeface.createFromAsset(getAssets(), "serif");
        //tv.setTypeface(face);
        // Defining the layout parameters of the TextView
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        // Setting the parameters on the TextView
        //tv.setLayoutParams(lp);

        // Adding the TextView to the RelativeLayout as a child
        frameLayout.addView(tv);

        ImageButton playButton = new ImageButton();






    }
}

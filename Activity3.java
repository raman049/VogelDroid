package com.vogelplay.vogel3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;



public class Activity3 extends AppCompatActivity {

    PopupWindow popUpWindow;
    ImageButton scoreboard;
    FrameLayout frameLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Creating a new FrameLayout
        frameLayout3 = new FrameLayout(this);
        frameLayout3.setBackgroundColor(Color.GRAY);
//        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       getSupportActionBar().hide();
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
//        // Setting the RelativeLayout as our content view
        setContentView(frameLayout3);
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                1000, 320);
        flp.setMargins(width / 2 - 500, height / 6, 0, 0);
//        // Creating a new TextView
        TextView tv = new TextView(this);
        tv.setText("VOGEL");
        tv.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLUE);
        tv.setTextSize(100);
       tv.setLayoutParams(flp);
        frameLayout3.addView(tv);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(150, 150);
        lp.setMargins(width / 2 - 75, height / 2, 0, 0);
        ImageButton playButton = new ImageButton(this);
        playButton.setLayoutParams(lp);
        playButton.setBackgroundResource(R.drawable.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Connect();
                Intent i = new Intent(com.vogelplay.vogel3.Activity3.this, Activity2.class);
                startActivity(i);
                //Toast toast = Toast.makeText(MainActivity.this, "You clicked button " + v.getId(), Toast.LENGTH_LONG);
                // toast.show();
            }
        });
        frameLayout3.addView(playButton);

        TextView highScore = new TextView(this);
        FrameLayout.LayoutParams lpHS = new FrameLayout.LayoutParams(700, 150);
        lpHS.setMargins(width / 2 - 350, height - 200, 0, 0);
        highScore.setText("HIGH SCORE");

        highScore.setTypeface(Typeface.create("Comic Sans MS", Typeface.NORMAL));
        highScore.setGravity(Gravity.CENTER);
        highScore.setTextColor(Color.YELLOW);
        highScore.setTextSize(25);
        highScore.setLayoutParams(lpHS);
        frameLayout3.addView(highScore);

        final ImageButton instruction = new ImageButton(this);
        popUpWindow = new PopupWindow(this);
        FrameLayout.LayoutParams lpInst = new FrameLayout.LayoutParams(150, 150);
        lpInst.setMargins(10, height - 170, 0, 0);
        instruction.setLayoutParams(lpInst);
        instruction.setBackgroundResource(R.drawable.instructionque);
        frameLayout3.addView(instruction);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instruction.invalidate();
                scoreboard.invalidate();
                popUpWindow.showAtLocation(frameLayout3, Gravity.CENTER, 0, 0);
                final String ss = "skdjf";
                Log.d(ss, "onClick");
            }
        });
        ImageView instView = new ImageView(com.vogelplay.vogel3.Activity3.this);
        FrameLayout.LayoutParams lpinstView = new FrameLayout.LayoutParams(width - 100, height - 100);
        lpinstView.setMargins(0, 0, 0, 0);
        instView.setLayoutParams(lpinstView);
        instView.setImageResource(R.drawable.instruction2);
        FrameLayout popupFrame = new FrameLayout(this);
        popupFrame.addView(instView);

        ImageButton backButton = new ImageButton(this);
        FrameLayout.LayoutParams lpbackButton = new FrameLayout.LayoutParams(150, 160);
        lpbackButton.setMargins(10, 10, 0, 0);
        backButton.setLayoutParams(lpbackButton);
        backButton.setBackgroundResource(R.drawable.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instruction.isActivated();
                scoreboard.isActivated();
                popUpWindow.dismiss();
            }
        });
        popupFrame.addView(backButton);
        popUpWindow.setContentView(popupFrame);


        scoreboard = new ImageButton(this);
        FrameLayout.LayoutParams lpscoreb = new FrameLayout.LayoutParams(150, 150);
        lpscoreb.setMargins(170, height - 170, 0, 0);
        scoreboard.setLayoutParams(lpscoreb);
        scoreboard.setBackgroundResource(R.drawable.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(com.vogelplay.vogel3.Activity3.this, "You clicked button " + v.getId(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
        frameLayout3.addView(scoreboard);

    }


}

package com.vogelplay.vogel3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    PopupWindow popUpWindow;
    ImageButton scoreboard;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // Creating a new FrameLayout
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(Color.RED);
        // Setting the RelativeLayout as our content view
        setContentView(frameLayout);
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                width/2,height/3);
        flp.setMargins(width/4,height/6,0,0);
 // TEXT_VIEW VOGEL
        TextView tv = new TextView(this);
        tv.setText("VOGEL");
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/COMIC.TTF");
        tv.setTypeface(face);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLUE);
        tv.setTextSize(width/20);
        tv.setLayoutParams(flp);
        frameLayout.addView(tv);
 // PLAY BUTTON
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width/10, height/6);
        lp.setMargins(width*9/20,height/2,0,0);
        ImageButton playButton = new ImageButton(this);
        playButton.setLayoutParams(lp);
        playButton.setBackgroundResource(R.drawable.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Activity2.class);
                startActivity(i);
            }
        });
        frameLayout.addView(playButton);
 // HIGHSCORE TEXT
        TextView highScore = new TextView(this);
        FrameLayout.LayoutParams lpHS = new FrameLayout.LayoutParams(width/2, height/3);
        lpHS.setMargins(width/4 ,height*3/4 ,0,0);
        highScore.setText("HIGH SCORE: 00000000");
        highScore.setTypeface(face);
        highScore.setGravity(Gravity.CENTER);
        highScore.setTextColor(Color.YELLOW);
        highScore.setTextSize(height/50);
        highScore.setLayoutParams(lpHS);
        frameLayout.addView(highScore);

        final ImageButton instruction = new ImageButton(this);
        popUpWindow = new PopupWindow(this);
        FrameLayout.LayoutParams lpInst = new FrameLayout.LayoutParams(width/10, height/6);
        lpInst.setMargins(10,height*6/7 -30,0,0);
        instruction.setLayoutParams(lpInst);
        instruction.setBackgroundResource(R.drawable.instructionque);
        frameLayout.addView(instruction);
        instruction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                instruction.invalidate();
                scoreboard.invalidate();
                popUpWindow.showAtLocation(frameLayout,Gravity.CENTER,0,0);
                final String ss="skdjf";
                Log.d(ss,"onClick");
            }
        });
        ImageView instView = new ImageView(MainActivity.this);
        FrameLayout.LayoutParams lpinstView = new FrameLayout.LayoutParams(width -10, height-10);
        lpinstView.setMargins(0,0,0,0);
        instView.setLayoutParams(lpinstView);
        instView.setImageResource(R.drawable.instruction2);
        FrameLayout popupFrame = new FrameLayout(this);
        popupFrame.addView(instView);

        ImageButton backButton = new ImageButton(this);
        FrameLayout.LayoutParams lpbackButton = new FrameLayout.LayoutParams(width/10, height/6);
        lpbackButton.setMargins(10,10,0,0);
        backButton.setLayoutParams(lpbackButton);
        backButton.setBackgroundResource(R.drawable.close);
        backButton.setOnClickListener(new View.OnClickListener(){
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
        FrameLayout.LayoutParams lpscoreb = new FrameLayout.LayoutParams(width/10, height/6);
        lpscoreb.setMargins(width/10 +20,height*6/7 -30,0,0);
        scoreboard.setLayoutParams(lpscoreb);
        scoreboard.setBackgroundResource(R.drawable.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "You clicked button " + v.getId(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
        frameLayout.addView(scoreboard);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

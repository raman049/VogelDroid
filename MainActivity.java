package com.vogelplay.vogel3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
    MediaPlayer loop1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
                width*2/3,WindowManager.LayoutParams.WRAP_CONTENT);
        flp.setMargins(width/6,height/7,0,0);
 // TEXT_VIEW VOGEL
        TextView tv = new TextView(this);
        tv.setText("VOGEL");
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/COMIC.TTF");
        tv.setTypeface(face);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLUE);
        tv.setTextSize(height/10);
        tv.setLayoutParams(flp);
        frameLayout.addView(tv);

//ADD SOUND LOOP 1
        loop1 = MediaPlayer.create(this,R.raw.intro1);
        loop1.setLooping(true);
        loop1.start();
//PLAY BUTTON
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width/10, width/10);
        lp.setMargins(width*9/20,height/2,0,0);
        ImageButton playButton = new ImageButton(this);
        playButton.setLayoutParams(lp);
        playButton.setBackgroundResource(R.drawable.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop1.stop();
                Intent i = new Intent(MainActivity.this,Activity2.class);
                startActivity(i);
            }
        });
        frameLayout.addView(playButton);
 // HIGHSCORE TEXT
        TextView highScore = new TextView(this);
        FrameLayout.LayoutParams lpHS = new FrameLayout.LayoutParams(width/2, WindowManager.LayoutParams.WRAP_CONTENT);
        lpHS.setMargins(width/4 ,height*6/7 -30 ,0,0);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore_String;
        if (prefs.getBoolean("myPrefsKey",true)){
             highScore_String = prefs.getInt("score", 0);
        }else {
            prefs.edit().putInt("score", 0).commit();
            highScore_String = prefs.getInt("score", 0);
        }
        highScore.setText("HIGH SCORE: "+highScore_String);
        highScore.setTypeface(face);
        highScore.setGravity(Gravity.CENTER);
        highScore.setTextColor(Color.YELLOW);
        highScore.setTextSize(height/40);
        highScore.setLayoutParams(lpHS);
        frameLayout.addView(highScore);

        final ImageButton instruction = new ImageButton(this);
        popUpWindow = new PopupWindow(this);
        FrameLayout.LayoutParams lpInst = new FrameLayout.LayoutParams(width/10, width/10);
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
        FrameLayout.LayoutParams lpbackButton = new FrameLayout.LayoutParams(width/10, width/10);
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
        FrameLayout.LayoutParams lpscoreb = new FrameLayout.LayoutParams(width/10, width/10);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        loop1.stop();
        loop1.release();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loop1 = MediaPlayer.create(this,R.raw.intro1);
        loop1.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

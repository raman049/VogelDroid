package com.vogelplay.vogel3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Activity3 extends AppCompatActivity {

    PopupWindow popUpWindow;
    ImageButton scoreboard;
    FrameLayout frameLayout3;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    ShareDialog shareDialog;
    InterstitialAd mInterstitialAd;
    Bitmap snapImage;
    int width,height;
    MediaPlayer loop1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        frameLayout3 = new FrameLayout(this);
        frameLayout3.setBackgroundColor(Color.argb(255, 0, 0, 200));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
//  Setting the RelativeLayout as our content view
        setContentView(frameLayout3);
         width = this.getResources().getDisplayMetrics().widthPixels;
         height = this.getResources().getDisplayMetrics().heightPixels;

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

//BANNAR ADS
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
         adView.setLayoutParams(params);
        adView.setAdUnitId("ca-app-pub-7941365967795667/9898703231");
        AdRequest adRequest = new AdRequest.Builder().build();
         adView.loadAd(adRequest);
// Place the ad view.
      //   frameLayout3.addView(adView);
        // MobileAds.initialize(getApplicationContext(), "ca-app-pub-7941365967795667/9898703231");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
          final Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
             @Override
             public void run() {
              //   showAds();
                 handler.postDelayed(this, 500);
             }
          }, 500);


        FrameLayout.LayoutParams flpHighScore = new FrameLayout.LayoutParams(1000, 320);
        flpHighScore.setMargins(width / 3 - 500, height / 10, 0, 0);
        TextView highScore = new TextView(this);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore_String = prefs.getInt("score", 0);
        highScore.setText("High Score: \n"+highScore_String);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/comici.ttf");
        highScore.setTypeface(face);
        highScore.setGravity(Gravity.CENTER);
        highScore.setTextColor(Color.YELLOW);
        highScore.setTextSize(20);
        highScore.setLayoutParams(flpHighScore);
        frameLayout3.addView(highScore);
//PRESENT SCORE
        FrameLayout.LayoutParams flpScore = new FrameLayout.LayoutParams(
                1000, 320);
        flpScore.setMargins(width - width / 3 - 500, height / 10, 0, 0);
        TextView presentScore = new TextView(this);
       Intent get_score = getIntent();
        int final_score = get_score.getIntExtra("final_score",0);
        presentScore.setText("Your Score: \n"+final_score);
        presentScore.setTypeface(face);
        presentScore.setGravity(Gravity.CENTER);
        presentScore.setTextColor(Color.YELLOW);
        presentScore.setTextSize(20);
        presentScore.setLayoutParams(flpScore);
        frameLayout3.addView(presentScore);
// REPLAY
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width / 10, height / 6);
        lp.setMargins(width * 9 / 10 - 10, height * 6 / 7 - 30, 0, 0);
        ImageButton replayButton = new ImageButton(this);
        replayButton.setLayoutParams(lp);
        replayButton.setBackgroundResource(R.drawable.replay);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop1.stop();
                Intent i = new Intent(com.vogelplay.vogel3.Activity3.this, Activity2.class);
                startActivity(i);
            }
        });
        frameLayout3.addView(replayButton);
///INSTRUCTION
        final ImageButton instruction = new ImageButton(this);
        popUpWindow = new PopupWindow(this);
        FrameLayout.LayoutParams lpInst = new FrameLayout.LayoutParams(width / 10, height / 6);
        lpInst.setMargins(10, height * 6 / 7 - 30, 0, 0);
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
        FrameLayout.LayoutParams lpbackButton = new FrameLayout.LayoutParams(width / 10, height / 6);
        lpbackButton.setMargins(10, 10, 0, 0);
        backButton.setLayoutParams(lpbackButton);
        backButton.setBackgroundResource(R.drawable.close);
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
        //FB BUTTON
        FrameLayout.LayoutParams lpFb = new FrameLayout.LayoutParams(width / 10, height / 6);
        lpFb.setMargins(10, height * 29 / 42 - 40, 0, 0);
        ImageButton fbButton = new ImageButton(this);
        fbButton.setLayoutParams(lpFb);
        fbButton.setBackgroundResource(R.drawable.fblogo);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishImage();
            }
        });
        frameLayout3.addView(fbButton);
//  SCOREBOARD BUTTON
        scoreboard = new ImageButton(this);
        FrameLayout.LayoutParams lpscoreb = new FrameLayout.LayoutParams(width / 10, height / 6);
        lpscoreb.setMargins(width / 10 + 20, height * 6 / 7 - 30, 0, 0);
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
//  BIRD IMAGE
        ImageView injured_bird = new ImageView(this);
        injured_bird.setImageResource(R.drawable.injuredbird);
        FrameLayout.LayoutParams lp_injured_bird = new FrameLayout.LayoutParams(800, 800);
        lp_injured_bird.setMargins(width / 2 - 400, height / 10 + 150, 0, 0);
        injured_bird.setLayoutParams(lp_injured_bird);
        frameLayout3.addView(injured_bird);


        final Handler handler2 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                snapImage = screenShot(getWindow().getDecorView().getRootView());
            }
        }, 500);
  //ADD SOUND
        loop1 = MediaPlayer.create(this,R.raw.intro1);
        loop1.setLooping(true);
        loop1.start();

    }

    public void publishImage() {
        Bitmap image = snapImage;
                //BitmapFactory.decodeResource(getResources(), R.drawable.injuredbird);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareDialog.show(content);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity3.this, MainActivity.class);
        startActivity(i);
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
    Boolean adsCheck = false;
    public void showAds() {
        if(adsCheck == false) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                Log.d("ads", "ads wtf");
                adsCheck = true;
            } else {
                Log.d("ads", "ads Mesg");
            }
        }
    }
    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}

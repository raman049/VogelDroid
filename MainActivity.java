package com.vogelplay.vogel_4;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    PopupWindow popUpWindow;
    ImageButton scoreboard;
    FrameLayout frameLayout;
    MediaPlayer loop1;
    int highScore_String;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(Color.RED);
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

//PLAY BUTTON
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width/10, width/10);
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
        FrameLayout.LayoutParams lpHS = new FrameLayout.LayoutParams(width/2, WindowManager.LayoutParams.WRAP_CONTENT);
        lpHS.setMargins(width/4 ,height*6/7 -30 ,0,0);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
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

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

       // GoogleApiAvailability api = GoogleApiAvailability.getInstance();
       // final int code = api.isGooglePlayServicesAvailable(this);
        scoreboard = new ImageButton(this);
        FrameLayout.LayoutParams lpscoreb = new FrameLayout.LayoutParams(width/10, width/10);
        lpscoreb.setMargins(width/10 +20,height*6/7 -30,0,0);
        scoreboard.setLayoutParams(lpscoreb);
        scoreboard.setBackgroundResource(R.drawable.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mGoogleApiClient.isConnected()){
                // Do Your Stuff Here
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        getString(R.string.leaderboard_highscore)), 0);

            } else {
                Toast toast = Toast.makeText(MainActivity.this, "Restart and Sign in with Google Play Services ;)", Toast.LENGTH_LONG);
                toast.show();
            }
            }
        });
        frameLayout.addView(scoreboard);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        loop1 = MediaPlayer.create(this,R.raw.intro1);
        loop1.setLooping(true);
        loop1.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loop1.stop();
        loop1.release();
    }

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "error")) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
//                BaseGameUtils.showActivityResultError(this,
//                        requestCode, resultCode, R.string.signin_failure);
                Toast toast = Toast.makeText(MainActivity.this, "Restart and Sign in with Google Play Services ;)", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}

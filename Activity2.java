package com.vogelplay.vogel3;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
//import android.*;
import java.util.Random;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {
    boolean gameOver, started;
    DrawView drawView;
    FrameLayout frameLayout, frameLayout2;
    int width, height;
    Typeface face;
    Paint rectPaint;
    TextView tap,high_score,score;
    ImageView birdImage,ship,jetY,jetP,cloud1,cloud2,cloud3;
    FrameLayout.LayoutParams flpBird,fl_jetY,fl_jetP,fl_cloud1,fl_cloud2,fl_cloud3,fl_ship;
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawView = new DrawView(this);
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(Color.argb(255,153,204,255));
        setContentView(frameLayout);
        super.onCreate(savedInstanceState);
        width = this.getResources().getDisplayMetrics().widthPixels;
        height = this.getResources().getDisplayMetrics().heightPixels;
        face = Typeface.createFromAsset(getAssets(), "fonts/comici.ttf");
        frameLayout2 = new FrameLayout(this);
        frameLayout.addView(frameLayout2);
        drawView = new DrawView(this);
        frameLayout.addView(drawView);
        addTextT2S();
        addTextHighScore();
        addWave();
        addSun();
        addBird();
        addJetYellow(rectJetYellow);
        addJetPurple(rectJetPurple);
        addCloud1(rectCloud1);
        addCloud2(rectCloud2);
        addCloud3(rectCloud3);
        addShip(rectShip);
    }
    public class DrawView extends View {
        public DrawView(Context context) {
            super(context);
            rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            bird_rect = new Rect(width / 3, height / 2, width / 3 + 60, height / 2 + 30);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.BLACK);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);

            if (started == true && gameOver != true) {
                moveJet();
                moveCloud();
                moveShip();
                gravity();
                checkCollision();
            }
            if (gameOver == true) {
                canvas.drawText("GameOver", width / 2, height / 2, rectPaint);
            }
            canvas.drawRect(bird_rect, rectPaint);
            canvas.drawRect(rectJetYellow,rectPaint);
            canvas.drawRect(rectJetPurple,rectPaint);
            canvas.drawRect(rectCloud1,rectPaint);
            canvas.drawRect(rectCloud2,rectPaint);
            canvas.drawRect(rectCloud3,rectPaint);
            canvas.drawRect(rectShip,rectPaint);
            invalidate();
        }

        public void gravity() {
            if (started == true) {
                bird_rect.top += 5;
                bird_rect.bottom += 5;
                flpBird.setMargins(bird_rect.left - 30, bird_rect.top - 50, bird_rect.right, bird_rect.bottom);
                birdImage.setLayoutParams(flpBird);
            }
        }

        public void checkCollision() {

            Rect ab = bird_rect;
            Rect bb = rectJetYellow;
            if (ab.intersect(bb)) {
                gameOver = true;
            }
            if (bird_rect.bottom < 0) {
                gameOver = true;
            }
            if (gameOver == true) {
                frameLayout.removeView(high_score);
                addTextHighScore();
            }
        }

        public void fly() {
            bird_rect.top -= 180;
            bird_rect.bottom -= 180;
            flpBird.setMargins(bird_rect.left - 30, bird_rect.top - 50, bird_rect.right, bird_rect.bottom);
            birdImage.setLayoutParams(flpBird);
            try {
                Thread.sleep(9);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            fly();

            if (started != true) {
                started = true;
                frameLayout.removeView(tap);
                addTextScore();
                frameLayout.removeView(high_score);
                addTextHighScore();
            }

            if ( event.getAction() == MotionEvent.ACTION_UP){
                birdImage.setImageResource(R.drawable.bird2);
            }else if ( event.getAction() == MotionEvent.ACTION_DOWN){
                birdImage.setImageResource(R.drawable.bird1);
            }
            return super.onTouchEvent(event);
        }
    }
    public void addTextT2S(){
    tap = new TextView(this);
    tap.setTypeface(face);
    tap.setText("Tap To Start");
    tap.setGravity(Gravity.CENTER);
    tap.setTextColor(Color.YELLOW);
    tap.setTextSize(75);
    FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
            1000,200);
    flpTtoS.setMargins(width/2 - 550,height/2 -200,0,0);
    tap.setLayoutParams(flpTtoS);
    frameLayout.addView(tap);
}
    public void addTextHighScore(){
        high_score = new TextView(this);
        high_score.setTypeface(face);
        high_score.setGravity(Gravity.CENTER);
        high_score.setTextColor(Color.RED);
        high_score.setTextSize(25);
        String high_score_sting = "High Score: \n 00000000";
        high_score.setText(high_score_sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                1000,200);
        flpTtoS.setMargins(width/2 - 500,height/6,0,0);
        //high_score.setBackgroundColor(Color.DKGRAY);
        high_score.setLayoutParams(flpTtoS);

        if(started == true){
            high_score.setTextColor(Color.BLUE);
            high_score.setTextSize(15);
            high_score.setGravity(Gravity.LEFT);
            flpTtoS.setMargins(10,100,0,0);
            high_score.setLayoutParams(flpTtoS);
        }
        frameLayout.addView(high_score);
    }
    public void addTextScore(){
        score = new TextView(this);
        score.setTypeface(face);
        score.setGravity(Gravity.LEFT);
        score.setTextColor(Color.BLUE);
        score.setTextSize(16);
        String score_sting = "Score: \n 00000000";
        score.setText(score_sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                1000,200);
        flpTtoS.setMargins(10,200,0,0);
        score.setLayoutParams(flpTtoS);
        frameLayout.addView(score);
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
            move_wave.setInterpolator(new LinearInterpolator());
            move_wave.setDuration(1000);
            move_wave.setRepeatCount(-1);
            wave.startAnimation(move_wave);
            frameLayout.addView(wave);
            ImageView wave2 = new ImageView(this);
            wave2.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
                    400, 200);
            flpWave2.setMargins((400*i)-605, height - 170, 0, 0);
            wave2.setLayoutParams(flpWave2);
            TranslateAnimation move_wave2 = new TranslateAnimation(0, 600, 0, 0);
            move_wave2.setInterpolator(new LinearInterpolator());
            move_wave2.setDuration(800);
            move_wave2.setRepeatCount(-1);
            wave2.startAnimation(move_wave2);
            frameLayout.addView(wave2);
        }

    }
    Rect bird_rect;
    Rect rectJetYellow = jetRect();
    Rect rectJetPurple = jetRect();
    Rect rectCloud1 = cloudRect();
    Rect rectCloud2 = cloudRect();
    Rect rectCloud3 = cloudRect();
    Rect rectShip = shipRect();
    public Rect shipRect() {
        Random randomX1 = new Random();
        int randomXX= 1+randomX1.nextInt(500);
        int randomYY = height-300 + randomX1.nextInt(10);
        int rectL = 1-500 - randomXX;
        int rectT= randomYY;
        Rect shipPurp =new Rect( rectL, rectT,rectL + 150 , rectT + 70);
        return shipPurp;
    }
    public void addShip(Rect rect) {
        rect.left += 10;
        rect.right += 10;
        frameLayout2.removeView(ship);
        fl_ship = new FrameLayout.LayoutParams(
                550, 400);
        fl_ship.setMargins(rect.left-85, rect.top-75, rect.left + 200, rect.top + 200);
        ship = new ImageView(this);
        ship.setImageResource(R.drawable.ship2);
        ship.setLayoutParams(fl_ship);
        frameLayout2.addView(ship);
    }
    public void moveShip() {
        rectShip.left += 10;
        rectShip.right += 10;
        fl_ship.setMargins(rectShip.left - 85, rectShip.top - 75, rectShip.left + 200, rectShip.top + 200);
        ship.setLayoutParams(fl_ship);
        if (rectShip.left > width) {
            rectShip = shipRect();
        }
    }
    public void addSun(){
        String w = Integer.toString(width);
        String h = Integer.toString(height);
        Log.d("width",w);
        Log.d("height",h);
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                width/13,width/13);
        flpSun.setMargins(width - width/8 , height/11,0,0);
        ImageView sunImage = new ImageView(this);
        sunImage.setImageResource(R.drawable.sun);
        sunImage.setLayoutParams(flpSun);
        frameLayout.addView(sunImage);
        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -360f, width/26,width/26);
        rotate_sun.setDuration(10000);
        rotate_sun.setRepeatCount(-1);
        rotate_sun.setInterpolator(new LinearInterpolator());
        sunImage.startAnimation(rotate_sun);
    }
    public void addBird(){
        flpBird = new FrameLayout.LayoutParams(
                150,150);
        flpBird.setMargins(bird_rect.left -30, bird_rect.top-50,bird_rect.right-75,bird_rect.bottom-75);
        birdImage = new ImageView(this);
        birdImage.setImageResource(R.drawable.bird1);
        birdImage.setLayoutParams(flpBird);
       // birdImage.setBackgroundColor(Color.WHITE );
        frameLayout2.addView(birdImage);
    }
    public Rect jetRect() {
        Random randomX1 = new Random();
        int randomXX= 200+randomX1.nextInt(1000);
        int randomYY = randomX1.nextInt(1000);
        int rectL = 1500 + randomXX;
        int rectT= 200+randomYY;
        Rect jetPurp =new Rect( rectL, rectT,rectL + 150 , rectT + 70);
        return jetPurp;
    }
    public void addJetYellow(Rect rect) {
        rect.left -= 10;
        rect.right -= 10;
        frameLayout2.removeView(jetY);
        fl_jetY = new FrameLayout.LayoutParams(
                350, 200);
        fl_jetY.setMargins(rect.left-75, rect.top-50, rect.left + 200, rect.top + 200);
        jetY = new ImageView(this);
        jetY.setImageResource(R.drawable.plane2);
        jetY.setLayoutParams(fl_jetY);
        frameLayout2.addView(jetY);
    }
    public void addJetPurple(Rect rect) {
        rect.left -= 10;
        rect.right -= 10;
        frameLayout2.removeView(jetP);
        fl_jetP = new FrameLayout.LayoutParams(
                350, 200);
        fl_jetP.setMargins(rect.left-75, rect.top-50, rect.left + 200, rect.top + 200);
        jetP = new ImageView(this);
        jetP.setImageResource(R.drawable.plane1);
        jetP.setLayoutParams(fl_jetP);
        frameLayout2.addView(jetP);
    }
    public void moveJet(){
        //JET YELLOW MOTION
        rectJetYellow.left -= 10;
        rectJetYellow.right -=10;
        fl_jetY.setMargins(rectJetYellow.left-75, rectJetYellow.top-50, rectJetYellow.left + 200, rectJetYellow.top + 200);
        jetY.setLayoutParams(fl_jetY);
        if (rectJetYellow.right < 0) {
            rectJetYellow = jetRect();
        }
        //JET PURPLE MOTION
        rectJetPurple.left -= 15;
        rectJetPurple.right -=15;
        fl_jetP.setMargins(rectJetPurple.left-75, rectJetPurple.top-50, rectJetPurple.left + 200, rectJetPurple.top + 200);
        jetP.setLayoutParams(fl_jetP);
        if (rectJetPurple.right < 0) {
            rectJetPurple = jetRect();
        }
    }
    public Rect cloudRect() {
        Random randomX1 = new Random();
        int randomXX= 200+randomX1.nextInt(1000);
        int randomYY = 50+randomX1.nextInt(200);
        int rectL = 1500 + randomXX;
        int rectT= randomYY;
        Rect cloudrect =new Rect( rectL, rectT,rectL + 150 , rectT + 70);
        return cloudrect;
    }
    public void addCloud1(Rect rect) {
        frameLayout2.removeView(cloud1);
        fl_cloud1 = new FrameLayout.LayoutParams(
                width/5, width/7);
        fl_cloud1.setMargins(rect.left-width/5, rect.top-width/7, rect.left + 200, rect.top + 200);
        cloud1 = new ImageView(this);
        cloud1.setImageResource(R.drawable.cloud1);
        cloud1.setLayoutParams(fl_cloud1);
        frameLayout2.addView(cloud1);
    }
    public void addCloud2(Rect rect) {
        frameLayout2.removeView(cloud2);
        fl_cloud2 = new FrameLayout.LayoutParams(
                width/5, width/7);
        fl_cloud2.setMargins(rect.left-85, rect.top-75, rect.left + 200, rect.top + 200);
        cloud2 = new ImageView(this);
        cloud2.setImageResource(R.drawable.cloud2);
        cloud2.setLayoutParams(fl_cloud2);
        frameLayout2.addView(cloud2);
    }
    public void addCloud3(Rect rect) {
        frameLayout2.removeView(cloud3);
        fl_cloud3 = new FrameLayout.LayoutParams(
                width/5, width/7);
        fl_cloud3.setMargins(rect.left-85, rect.top-75, rect.left + 200, rect.top + 200);
        cloud3 = new ImageView(this);
        cloud3.setImageResource(R.drawable.cloud3);
        cloud3.setLayoutParams(fl_cloud3);
        frameLayout2.addView(cloud3);
    }
    public void moveCloud() {
        rectCloud1.left -= 10;
        rectCloud1.right -= 10;
        fl_cloud1.setMargins(rectCloud1.left - width/5, rectCloud1.top - width/7, rectCloud1.left + 200, rectCloud1.top + 200);
        cloud1.setLayoutParams(fl_cloud1);
        if (rectCloud1.right < 0) {
            rectCloud1 = cloudRect();
        }
        rectCloud2.left -= 10;
        rectCloud2.right -= 10;
        fl_cloud2.setMargins(rectCloud2.left - 85, rectCloud2.top - 75, rectCloud2.left + 200, rectCloud2.top + 200);
        cloud2.setLayoutParams(fl_cloud2);
        if (rectCloud2.right < 0) {
            rectCloud2 = cloudRect();
        }
        rectCloud3.left -= 10;
        rectCloud3.right -= 10;
        fl_cloud3.setMargins(rectCloud3.left - 85, rectCloud3.top - 75, rectCloud3.left + 200, rectCloud3.top + 200);
        cloud3.setLayoutParams(fl_cloud3);
        if (rectCloud3.right < 0) {
            rectCloud3 = cloudRect();
        }
    }
}

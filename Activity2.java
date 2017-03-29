package com.vogelplay.vogel3;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
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
    //Paint high_score;
    DrawView drawView;
    FrameLayout frameLayout, frameLayout2;
    int width, height;
    Typeface face;
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawView = new DrawView(this);
        setContentView(drawView);
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(Color.argb(255,153,204,255));
        setContentView(frameLayout);
        super.onCreate(savedInstanceState);
        width = this.getResources().getDisplayMetrics().widthPixels;
        height = this.getResources().getDisplayMetrics().heightPixels;
        face = Typeface.createFromAsset(getAssets(), "fonts/comici.ttf");
        drawView = new DrawView(this);
        frameLayout.addView(drawView);
        frameLayout2 = new FrameLayout(this);
        frameLayout.addView(frameLayout2);
        addTextT2S();
        addTextHighScore();
        addJetY();
        addWave();
        addSun();

       // super.onCreate(savedInstanceState);

    }

    int canvas_width, canvas_height;
    Paint rectPaint;
    int yy,xx;
    public class DrawView extends View {
        String high_score_sting;

        public DrawView(Context context) {
            super(context);
            yy = getResources().getDisplayMetrics().heightPixels;
            xx = getResources().getDisplayMetrics().widthPixels;
             rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            rectJet_p = addRectJet1();
            rectJet_y = addRectJet2();
           // jetRecta = addRectJet1a();
            rectCloud = addRectCloud();
            bird_rect = new Rect(550,800,650, 900);
            bird_bit = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
            bird_bit = Bitmap.createScaledBitmap(bird_bit, 150, 100, true);

        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas_width = getWidth();
            canvas_height = getHeight();
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            canvas.drawBitmap(bird_bit, bird_rect.left+10, bird_rect.top+10, null);
            canvas.drawRect(bird_rect,rectPaint);

          //  if (started != true) {
                //HIGH SCORE
//                Paint high_score = new Paint();
//                high_score.setColor(Color.RED);
//                high_score.setTextSize(50);
//                high_score.setTypeface(face);
//                high_score_sting = "High Score: \n 00000000";
//                canvas.drawText(high_score_sting, canvas_width / 2 - 200, canvas_height / 6, high_score);
 //           }
            if (started == true && gameOver != true) {
//                high_score.setTextSize(25);
//                high_score.setColor(Color.BLUE);
//                high_score_sting = "High Score: \n 00000000";
//                canvas.drawText(high_score_sting, 10, 100, high_score);
//                high_score.setTextSize(27);
//                String score = "Score: \n 0000000";
//                canvas.drawText(score, 10, 150, high_score);
                gravity();
               jet1(canvas);
                cloud(canvas);
                checkCollision();

            }
                if (gameOver == true) {
                    canvas.drawText("GameOver", canvas_width / 2, canvas_height / 2, null);
                }
            invalidate();
        }

        Bitmap plane1_bit,plane2_bit,cloud_bit,ship_bit, bird_bit,wave2_bit;
        int rectL,rectT;
        Rect bird_rect;
        Rect rectJet_p,rectJet_y,rectCloud,rectShip;
        public Rect addRectJet1() {
                // position of jets
                Random randomX1 = new Random();
                int randomXX= 1000 + randomX1.nextInt(1000);
                int randomYY = 50+randomX1.nextInt(1000);
                rectL = 100 + 1000 + randomXX;
                rectT= 10+randomYY;
               Rect jetPurp =new Rect( rectL, rectT,rectL + 200 , rectT + 200);
            return jetPurp;
        }
        public void addJet1(Canvas jetCanvas, Rect jetRect) {
            jetRect.left -= 10;
            jetRect.right -= 10;
            jetCanvas.drawRect(jetRect, rectPaint);
            plane1_bit = BitmapFactory.decodeResource(getResources(), R.drawable.plane1);
            plane1_bit = Bitmap.createScaledBitmap(plane1_bit, 350, 200, true);
            jetCanvas.drawBitmap(plane1_bit, jetRect.left-10, jetRect.top-10, null);
        }
        public void jet1(final Canvas canvas){
            addJet1(canvas, rectJet_p);
            if (rectJet_p.right < 0){
                rectJet_p = addRectJet1();
            }
            addJet2(canvas,rectJet_y);
            if (rectJet_y.right < 0){
                rectJet_y = addRectJet2();
            }
        }
        public Rect addRectJet2() {
            Random randomX1 = new Random();
            int randomXX= 1000 + randomX1.nextInt(1000);
            int randomYY = 50+randomX1.nextInt(1000);
            rectL = 100 + 1000 + randomXX;
            rectT= 10+randomYY;
            Rect jetPurp =new Rect( rectL, rectT,rectL + 200 , rectT + 200);
            return jetPurp;
        }
        public void addJet2(Canvas jetCanvas, Rect jetRect) {
            jetRect.left -= 15;
            jetRect.right -= 15;
            jetCanvas.drawRect(jetRect, rectPaint);
            plane2_bit = BitmapFactory.decodeResource(getResources(), R.drawable.plane2);
            plane2_bit = Bitmap.createScaledBitmap(plane2_bit, 350, 200, true);
            jetCanvas.drawBitmap(plane2_bit, jetRect.left-10, jetRect.top-10, null);
        }
        public Rect addRectCloud() {
                Random randomX1 = new Random();
                int randomXX= 1000 + randomX1.nextInt(1000);
                int randomYY = 10 +randomX1.nextInt(100);
                rectL = 1000 + 100 + randomXX;
                rectT= 10+randomYY;
                Rect rectCloud = new Rect( rectL, rectT,rectL +200 , rectT + 200);
            return rectCloud;
        }
        public void addCloud1( Canvas cloudCanvas, Rect cloudRect) {
            cloudRect.left -= 20;
            cloudRect.right -= 20;
            cloudCanvas.drawRect(cloudRect, rectPaint);
            cloud_bit = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
            cloud_bit = Bitmap.createScaledBitmap(cloud_bit, 350, 200, true);
            cloudCanvas.drawBitmap(cloud_bit, cloudRect.left-10, cloudRect.top-10, null);
        }
        public void cloud(Canvas canvas){
            addCloud1(canvas, rectCloud);
            if (rectCloud.right < 0){
                rectCloud = addRectCloud();
            }
        }
        public void gravity() {
            if (started == true) {
                bird_rect.top += 20;
                bird_rect.bottom += 20;
            }
        }

        public void checkCollision() {

            Rect ab = bird_rect;
            Rect bb = rectJet_p;
            if (ab.intersect(bb)) {
                gameOver = true;
            }
           }

public void fly(){
        bird_rect.top -= 180;
        bird_rect.bottom -=180;
}
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            fly();
            if (started != true) {
                started = true;
               frameLayout.removeView(tap);
                addShipImage();
                addTextScore();
            }

            return super.onTouchEvent(event);



        }

    }
    ImageView jetY;
    public void addJetY(){
        jetY = new ImageView(this);
        jetY.setImageResource(R.drawable.plane2);
        Random randomX1 = new Random();
        int randomXX= 800 + randomX1.nextInt(1000);
        int randomYY = 50+randomX1.nextInt(1000);
        int rectL = 100 + 1000 + randomXX;
        int rectT= 10+randomYY;

        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
                350, 200);
        fl_jet.setMargins(rectL, rectT, rectL +200, rectT+200);///////hhjhj

        TranslateAnimation move_jetY = new TranslateAnimation(0, -2000,0, 0);
        move_jetY.setDuration(8000);
        move_jetY.setRepeatCount(0);
        move_jetY.setRepeatMode(1);
        move_jetY.reset();
        jetY.setBackgroundColor(Color.BLACK);
        jetY.setLayoutParams(fl_jet);
        jetY.startAnimation(move_jetY);
        frameLayout.addView(jetY);

    }
    public Rect addRectJet1a() {
        // position of jets
        Random randomX1 = new Random();
        int randomXX= 1000 + randomX1.nextInt(1000);
        int randomYY = 50+randomX1.nextInt(1000);
        int rectL = 100 + 1000 + randomXX;
       int rectT= 10+randomYY;
        Rect jetPurp =new Rect( rectL, rectT,rectL + 200 , rectT + 200);
        return jetPurp;
    }
    public void addJet1a() {
        jetRecta = addRectJet1a();
        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
                350, 200);
        fl_jet.setMargins(jetRecta.left, jetRecta.top, jetRecta.left + 200, jetRecta.top + 200);///////hhjhj
        jetRecta.left -= 10;
        jetRecta.right -= 10;
        jetY = new ImageView(this);
        jetY.setImageResource(R.drawable.plane2);
        jetY.setLayoutParams(fl_jet);
        TranslateAnimation move_jetY = new TranslateAnimation(0, -5000, 0, 0);
        move_jetY.setDuration(4000);
        move_jetY.setRepeatCount(1);
        move_jetY.setRepeatMode(1);
       // jetY.setBackgroundColor(Color.BLACK);
        jetY.startAnimation(move_jetY);
        frameLayout.addView(jetY);

    }
    Rect jetRecta;
    public void jet1a() {
            addJet1a();
    }
    TextView tap,high_score,score;
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
        flpTtoS.setMargins(width/2 - 200,height/6,0,0);
        high_score.setLayoutParams(flpTtoS);
        frameLayout.addView(high_score);
    }
    public void addTextScore(){
        score = new TextView(this);
        score.setTypeface(face);
        score.setGravity(Gravity.CENTER);
        score.setTextColor(Color.BLUE);
        score.setTextSize(14);
        String score_sting = "Score: \n 00000000";
        score.setText(score_sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                1000,200);
        flpTtoS.setMargins(10,150,0,0);
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
    ImageView ship;
    public void addShipImage(){
        ship = new ImageView(this);
        ship.setImageResource(R.drawable.ship2);
        Random random1 = new Random();
        int ramdom11 = height-450 + random1.nextInt(10);
        FrameLayout.LayoutParams fl_ship = new FrameLayout.LayoutParams(
                550, 400);
        fl_ship.setMargins(0 - 300, ramdom11, 0, 0);
        ship.setLayoutParams(fl_ship);
        TranslateAnimation move_ship = new TranslateAnimation(0, width+600, 0, 0);
        move_ship.setInterpolator(new LinearInterpolator());
        move_ship.setDuration(9000);
        move_ship.setRepeatCount(-1);
        move_ship.reset();
        ship.startAnimation(move_ship);

        frameLayout2.addView(ship);
    }
    public void addSun(){
        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
                150,150);
        flpSun.setMargins(width - 300 , 100,0,0);
        ImageView sunImage = new ImageView(this);
        sunImage.setImageResource(R.drawable.sun);
        sunImage.setLayoutParams(flpSun);
        frameLayout.addView(sunImage);
        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -360f, 75,75);
        rotate_sun.setDuration(10000);
        rotate_sun.setRepeatCount(-1);
        rotate_sun.setInterpolator(new LinearInterpolator());
        sunImage.startAnimation(rotate_sun);
    }

}

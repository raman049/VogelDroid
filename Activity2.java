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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//import android.*;
import java.util.Random;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {

    boolean gameOver, started;
    Paint high_score;
    DrawView drawView;

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawView = new DrawView(this);
        setContentView(drawView);




        super.onCreate(savedInstanceState);

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
            rectCloud = addRectCloud();
            rectShip = addRectShip();
            wave2Rect0 = addRectWave1();
            wave2Rect1 = addRectWave1();
            wave2Rect2 = addRectWave2();
            wave2Rect3 = addRectWave3();
            wave2Rect4 = addRectWave4();
            wave2Rect5 = addRectWave5();
            wave2Rect6 = addRectWave6();
            bird_rect = new Rect(550,800,650, 900);
            bird_bit = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
            bird_bit = Bitmap.createScaledBitmap(bird_bit, 150, 100, true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas_width = getWidth();
            canvas_height = getHeight();
            Paint tap2start = new Paint();
            tap2start.setStyle(Paint.Style.FILL);
            tap2start.setColor(Color.argb(255, 153, 204, 255));  //background color
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            canvas.drawRect(0, 0, getWidth(), getHeight(), tap2start);//background
            canvas.drawBitmap(bird_bit, bird_rect.left+10, bird_rect.top+10, null);
            canvas.drawRect(bird_rect,rectPaint);
            addSun(canvas);
            addWave(canvas);
            wave0(canvas);
            wave1(canvas);
            wave2(canvas);
            wave3(canvas);
            wave4(canvas);
            wave5(canvas);
            wave6(canvas);

            if (started != true) {
                Typeface face = Typeface.createFromAsset(getAssets(), "fonts/comici.ttf");
                tap2start.setColor(Color.YELLOW);
                tap2start.setTypeface(face);
                tap2start.setTextSize(100);
                tap2start.setStrokeWidth(1);
                tap2start.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Tap to Start", canvas_width / 2 - 100, canvas_height / 2, tap2start);
                //HIGH SCORE
                high_score = new Paint();
                high_score.setColor(Color.RED);
                high_score.setTextSize(50);
                high_score.setTypeface(face);
                high_score_sting = "High Score: \n 00000000";
                canvas.drawText(high_score_sting, canvas_width / 2 - 200, canvas_height / 6, high_score);
            }
            if (started == true && gameOver != true) {
                high_score.setTextSize(25);
                high_score.setColor(Color.BLUE);
                high_score_sting = "High Score: \n 00000000";
                canvas.drawText(high_score_sting, 10, 100, high_score);
                high_score.setTextSize(27);
                String score = "Score: \n 0000000";
                canvas.drawText(score, 10, 150, high_score);
                gravity();
                jet1(canvas);
                cloud(canvas);
                ship(canvas);
                checkCollision();

            }
                if (gameOver == true) {
                    canvas.drawText("GameOver", canvas_width / 2, canvas_height / 2, high_score);
                }
            invalidate();
        }


        public void addSun(Canvas sunCanvas) {
            Bitmap sun_bit;
            sun_bit = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
            sun_bit = Bitmap.createScaledBitmap(sun_bit, 100, 100, true);
            //RotateDrawable rotate_sun = new RotateDrawable();
            sunCanvas.rotate(360);
            sunCanvas.drawBitmap(sun_bit, canvas_width - 200, 100, null);
        }
        int x1, y1;
        public void addWave(Canvas waveCanvas) {
            Bitmap wave_bit;
            wave_bit = BitmapFactory.decodeResource(getResources(), R.drawable.wave2);
            wave_bit = Bitmap.createScaledBitmap(wave_bit, 400, 200, true);
            for (int i = 0; i < 7; i++) {
                x1=(400 * i) - 5 * i;
                y1= yy- 150;
                waveCanvas.drawBitmap(wave_bit,x1 , y1, null);
            }
        }
        public Rect addRectWave0() {
            rectL = 5-400;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave1() {
            rectL = (400 * 0) - 5 * 0;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave2() {
            rectL = (400 * 1) - 5 * 1;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave3() {
            rectL = (400 * 2) - 5 * 2;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave4() {
            rectL = (400 * 3) - 5 * 3;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave5() {
            rectL = (400 * 4) - 5 * 4;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public Rect addRectWave6() {
            rectL = (400 * 5) - 5 * 5;
            rectT=  yy - 150;
            Rect rectWave =new Rect( rectL, rectT,rectL + 400 , rectT + 200);
            return rectWave;
        }
        public void addWave2(Canvas canvas, Rect Rect){
            Rect.left += 25;
            Rect.right += 25;
            canvas.drawRect(wave2Rect1, rectPaint);
            wave2_bit = BitmapFactory.decodeResource(getResources(), R.drawable.wave2);
            wave2_bit = Bitmap.createScaledBitmap(wave2_bit, 400, 200, true);
            canvas.drawBitmap(wave2_bit, Rect.left-10, Rect.top-10, null);
        }


        Rect wave2Rect0,wave2Rect1,wave2Rect2,wave2Rect3,wave2Rect4,wave2Rect5,wave2Rect6;
        public void wave0(final Canvas canvas){
            addWave2(canvas, wave2Rect0);
            if(wave2Rect0.left > 0){
                wave2Rect0 = addRectWave0();
            }
        }
        public void wave1(final Canvas canvas){
            addWave2(canvas, wave2Rect1);
            if(wave2Rect1.left > 400-5){
                wave2Rect1 = addRectWave1();
            }
        }
        public void wave2(final Canvas canvas){
            addWave2(canvas, wave2Rect2);
            if(wave2Rect2.left > 400*2-5*2){
                wave2Rect2 = addRectWave2();
            }
        }
        public void wave3(final Canvas canvas){
            addWave2(canvas, wave2Rect3);
            if(wave2Rect3.left > 400*3-5*3){
                wave2Rect3 = addRectWave3();
            }
        }
        public void wave4(final Canvas canvas){
            addWave2(canvas, wave2Rect4);
            if(wave2Rect4.left > 400*4-5*4){
                wave2Rect4 = addRectWave4();
            }
        }
        public void wave5(final Canvas canvas){
            addWave2(canvas, wave2Rect5);
            if(wave2Rect5.left > 400*5-5*5){
                wave2Rect5 = addRectWave5();
            }
        }
        public void wave6(final Canvas canvas){
            addWave2(canvas, wave2Rect6);
            if(wave2Rect6.left > 400*6-5*6){
                wave2Rect6 = addRectWave6();
            }
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
            jetRect.left -= 25;
            jetRect.right -= 25;
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
            jetRect.left -= 35;
            jetRect.right -= 35;
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
        public Rect addRectShip() {
            Random randomX1 = new Random();
            int randomXX=   randomX1.nextInt(100);
            int randomYY = 1200+ randomX1.nextInt(10);
            rectL = 10 - randomXX;
            rectT= randomYY;
            Rect shipRect =new Rect( rectL, rectT,rectL + 200 , rectT + 200);
            return shipRect;
        }
        public void addShip(Canvas jetCanvas, Rect rect) {
            rect.left += 12;
            rect.right += 12;
            jetCanvas.drawRect(rect, rectPaint);
            ship_bit = BitmapFactory.decodeResource(getResources(), R.drawable.ship2);
            ship_bit = Bitmap.createScaledBitmap(ship_bit, 550, 300, true);
            jetCanvas.drawBitmap(ship_bit, rect.left-10, rect.top-10, null);
        }
        public void ship(final Canvas canvas){
            addShip(canvas, rectShip);
            if (rectShip.right > 2000){
                rectShip = addRectShip();
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
            }

            return super.onTouchEvent(event);



        }

    }
}

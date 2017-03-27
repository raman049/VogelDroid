package com.vogelplay.vogel3;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//import android.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {

    boolean gameOver, started;
    Paint high_score;
    DrawView drawView;
    Handler handlerJet1 = new Handler();

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

    public class DrawView extends View {
        String high_score_sting;

        public DrawView(Context context) {
            super(context);
            rj= addRectJet1();
            cloud1_rect_arr = new ArrayList<>();
            bird_rect = new Rect(550,800,650, 900);
            bird_bit = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
            bird_bit = Bitmap.createScaledBitmap(bird_bit, 150, 100, true);
        }

        @Override
        public void onDraw(final Canvas canvas) {
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

               // addCloudArray(true);

                    jett(canvas);


                for (Rect cloudC : cloud1_rect_arr){
                    addCloud1(canvas,cloudC);
                }
                gravity();


            }
                if (gameOver == true) {
                    canvas.drawText("GameOver", canvas_width / 2, canvas_height / 2, high_score);
                }

            invalidate();
        }

        class MyThread extends Thread {
        @Override
        public void run() {
            for(int i =0; i<100;i++){
                Message message = Message.obtain();
                message.arg1 = i;
                handlerJet1.sendMessage(message);
               try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
               }
//                if (i == 98){
//                    i =2;
//                }
            }
        }
    }

        public void addSun(Canvas sunCanvas) {
            Bitmap sun_bit;
            sun_bit = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
            sun_bit = Bitmap.createScaledBitmap(sun_bit, 100, 100, true);
            //RotateDrawable rotate_sun = new RotateDrawable();
            sunCanvas.rotate(360);
            sunCanvas.drawBitmap(sun_bit, canvas_width - 200, 100, null);
        }

        public void addWave(Canvas waveCanvas) {
            Bitmap wave_bit;
            wave_bit = BitmapFactory.decodeResource(getResources(), R.drawable.wave2);
            wave_bit = Bitmap.createScaledBitmap(wave_bit, 400, 200, true);
            for (int i = 0; i < 7; i++) {

                waveCanvas.drawBitmap(wave_bit, 0 + (400 * i) - 5 * i, canvas_height - 170, null);
            }
        }

        Bitmap plane1_bit,cloud_bit, bird_bit;
        ArrayList<Rect>cloud1_rect_arr;

        int rectL,rectT;
        Rect bird_rect;
        Rect jetPurp,rj;
        public Rect addRectJet1() {
                // position of jets
                Random randomX1 = new Random();
                int randomXX= 10 + randomX1.nextInt(1000);
                int randomYY = 10+randomX1.nextInt(1000);
                rectL = 100 + 1000 + randomXX;
                rectT= 10+randomYY;
                jetPurp =new Rect( rectL, rectT,rectL + 200 , rectT + 200);//(700,700,730,730);//
            return new Rect( rectL, rectT,rectL + 200 , rectT + 200);

        }

        public void addJets( Canvas jetCanvas, Rect jetRect) {
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            jetRect.left -= 20;
            jetRect.right -= 20;
            jetCanvas.drawRect(jetRect, rectPaint);
            plane1_bit = BitmapFactory.decodeResource(getResources(), R.drawable.plane1);
            plane1_bit = Bitmap.createScaledBitmap(plane1_bit, 350, 200, true);
            jetCanvas.drawBitmap(plane1_bit, jetRect.left-10, jetRect.top-10, null);

        }
        int aaa= 0;
       Handler ha;
        public void jett(final Canvas canvas){

            addJets(canvas,rj);
            if (rj.right < 0){
                rj = addRectJet1();
            }

//            ha = new Handler(){
//                @Override
//                public void handleMessage(Message me) {
//                   int a = me.arg2;
//                }
//            };
   // new Thread( new Task()).start();
        }

        class Task implements Runnable{
            @Override
            public void run() {

                for(int i = 0; i< 100; i++){
                    Message me = Message.obtain();
                    me.arg2 = i;
                    me.arg1 =1;
                    ha.sendMessage(me);
                    //rj= addRectJet1();
                   // jetPurp = new Rect();
                    try {
                        Thread.sleep(9000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }



        public void addCloudArray(boolean s) {
            if (s == true) {
                // position of cloud
                Random randomX1 = new Random();
                int randomXX= 10 + randomX1.nextInt(canvas_width+ 10);
                int randomYY = 10+randomX1.nextInt(200 +9);
                rectL = canvas_width + 100 + canvas_width* cloud1_rect_arr.size() + randomXX;
                //rectR = canvas_width + canvas_width* cloud1_rect_arr.size()+100 + 200 +randomXX;
                rectT= 10+randomYY;
                cloud1_rect_arr.add(new Rect( rectL, rectT,rectL +200 , rectT + 200));
            }
        }
        public void addCloud1( Canvas jetCanvas, Rect cloud1Rect) {
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
           // cloud1Rect.left -= 5;
           // cloud1Rect.right -= 5;
            jetCanvas.drawRect(cloud1Rect, rectPaint);
            cloud_bit = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
            cloud_bit = Bitmap.createScaledBitmap(cloud_bit, 350, 200, true);
            jetCanvas.drawBitmap(cloud_bit, cloud1Rect.left-10, cloud1Rect.top-10, null);
           // new Thread( new TaskAddCloud()).start();
        }
        class TaskAddCloud implements Runnable{
            @Override
            public void run() {
                for(int i = 0; i< cloud1_rect_arr.size(); i++){
                    Rect cRect1 = cloud1_rect_arr.get(i);
                    cRect1.left -= 5;
                    cRect1.right -= 5;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        public void gravity() {
            if (started == true) {
                bird_rect.top += 20;
                bird_rect.bottom += 20;

              //  new Thread(new TaskGravity()).start();
            }
        }
        class TaskGravity implements Runnable{
            @Override
            public void run() {
                for(int i = 0; i< 1000; i++){
                    bird_rect.top += 2;
                    bird_rect.bottom += 2;
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        public void checkCollision() {

//        //    Rect ab = ;
//          //  Rect bb = addBird(canvas);
//            if (ab.intersect(bb)) {
//                gameOver = true;
//            }
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


//            ImageView wave = new ImageView(this);
//            wave.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave.setMargins((390 * i) - 400, height - 170, 0, 0);
//            wave.setLayoutParams(flpWave);
//            TranslateAnimation move_wave = new TranslateAnimation(0, 2 * width + 600, 0, 0);
//            move_wave.setDuration(4000);
//            move_wave.setRepeatCount(-1);
//            // move_wave.setRepeatMode(2);
//            wave.startAnimation(move_wave);
//            frameLayout2.addView(wave);

//            ImageView wave2 = new ImageView(this);
//            wave2.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave2.setMargins((400*i)-605-width, height - 170, 0, 0);
//            wave2.setLayoutParams(flpWave2);
//            TranslateAnimation move_wave2 = new TranslateAnimation(0, 600 + 2*width, 0, 0);
//            move_wave2.setDuration(4000);
//            move_wave2.setRepeatCount(-1);
//            wave2.startAnimation(move_wave2);
//            frameLayout2.addView(wave2);
//
//            ImageView wave3 = new ImageView(this);
//            wave3.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave3 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave2.setMargins((400*i)-605-width, height - 170, 0, 0);
//            wave3.setLayoutParams(flpWave2);
//            TranslateAnimation move_wave3 = new TranslateAnimation(0, 300 + width, 0, 0);
//            move_wave3.setDuration(2000);
//            move_wave3.setRepeatCount(-1);
//            wave3.startAnimation(move_wave3);
//            frameLayout2.addView(wave3);
//
//            ImageView wave4 = new ImageView(this);
//            wave4.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave4 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave4.setMargins((400*i)-605+width/3, height - 170, 0, 0);
//            wave4.setLayoutParams(flpWave4);
//            TranslateAnimation move_wave4 = new TranslateAnimation(0, 300 +width, 0, 0);
//            move_wave4.setDuration(2000);
//            move_wave4.setRepeatCount(-1);
//            wave4.startAnimation(move_wave4);
//            frameLayout2.addView(wave4);
//
//            ImageView wave_1 = new ImageView(this);
//            wave.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave_1 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave_1.setMargins((390*i)-400, height - 170, 0, 0);
//            wave_1.setLayoutParams(flpWave_1);
//            TranslateAnimation move_wave_1 = new TranslateAnimation(0, width+2000, 0, 0);
//            move_wave_1.setDuration(3000);
//            move_wave_1.setRepeatCount(-1);
//            wave_1.startAnimation(move_wave_1);
//            frameLayout2.addView(wave_1);






//        frameLayout2.addView(tap);
//        //High Score
//        FrameLayout.LayoutParams frame_hs = new FrameLayout.LayoutParams(
//                1000,120);
//        frame_hs.setMargins(width/2 - 500,height/6,0,0);
//        high_score = new TextView(this);
//        high_score.setText("High Score: \n0000000000");
//        high_score.setTypeface(face);
//        high_score.setGravity(Gravity.CENTER);
//        high_score.setTextSize(18);
//        high_score.setTextColor(Color.RED);
//       // high_score.setBackgroundColor(Color.GRAY);
//        high_score.setLayoutParams(frame_hs);
//        frameLayout2.addView(high_score);
//
//        //Sun
//        FrameLayout.LayoutParams flpSun = new FrameLayout.LayoutParams(
//                100,100);
//        flpSun.setMargins(width - 300 , 100,0,0);
//        ImageView image = new ImageView(this);
//        image.setImageResource(R.drawable.sun);
//        image.setLayoutParams(flpSun);
//        frameLayout2.addView(image);
//        RotateAnimation rotate_sun = new RotateAnimation(0.0f, -360f, 50,50);
//        rotate_sun.setDuration(10000);
//        rotate_sun.setRepeatCount(-1);
//        rotate_sun.setRepeatMode(1);
//        image.startAnimation(rotate_sun);
//
//        //bird
//        flp_bird = new FrameLayout.LayoutParams(
//                100,100);
//        bird_x = width/3; bird_y= height/2;
//        flp_bird.setMargins(bird_x , bird_y,width/3 + 200,height/2 + 200);
//        bird = new ImageView(this);
//        bird.setImageResource(R.drawable.bird1);
//        bird.setLayoutParams(flp_bird);
//        bird.setBackgroundColor(Color.BLACK);
//        frameLayout2.addView(bird);
//       // bird_rect = new Rect(Math.round(bird.getX()),Math.round(bird.getY()), Math.round(bird.getX()) + 200, Math.round(bird.getY())+200);
//
////        thread = new Thread(new MyThread());
////        thread.start();
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (addStuff != true) {
//                    addShip();
//                    addJetB();
//                    addCloud1();
//                    addCloud2();
//                    addCloud3();
//                    addJetY();
//                    addStuff = true;
//                }
//            }
//        };
//        thread = new Thread(new MyThread());
//        thread.start();
//
//        bird_rect = new Rect(0,0,0,0);
//        jet_b_rect = new Rect(0,0,0,0);
//        jet_y_rect = new Rect(0,0,0,0);
//
//        //bird gravity motion thread2
//
//        handler2 = new Handler() {
//            @Override
//            public void handleMessage(Message message2) {
//                 gravity();
//            }
//        };
//        gravity_thread = new Thread(new Gravity_Thread());
//        gravity_thread.start();
//
//
//
//        collision_handler = new Handler() {
//            @Override
//            public void handleMessage(Message message3) {
//                //gravity();
//                 checkCollision();
//            }
//        };
//        collision_thread = new Thread(new CollisionThread());
//        collision_thread.start();
//        //wave
//        addWave();

 //   }

//    @Override
//    protected void onResume() {
//        Intent iii = new Intent(Activity2.this,MainActivity.class);
//        startActivity(iii);
//        super.onResume();
//    }

//
//    @Override
//    protected void onRestart() {
//        Intent iii = new Intent(Activity2.this,MainActivity.class);
//        startActivity(iii);
//        super.onRestart();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (gameOver == true){
//            Intent ii = new Intent(Activity2.this,Activity3.class);
//            startActivity(ii);
//        }
//
//
//        if (started == true) {
//            frameLayout2.removeView(tap);
//            high_score.setTextSize(12);
//            high_score.setTextColor(Color.BLUE);
//            high_score.setX(10);
//            high_score.setGravity(Gravity.LEFT);
//            high_score.setY(20);
//            //Your Score
//            FrameLayout.LayoutParams frame_score = new FrameLayout.LayoutParams(
//                    1000, 100);
//            frame_score.setMargins(10, 100, 0, 0);
//            your_score = new TextView(this);
//            your_score.setText("Score: \n 0000000000");
//            Typeface face=Typeface.createFromAsset(getAssets(),"fonts/comici.ttf");
//            your_score.setTypeface(face);
//            your_score.setTextSize(14);
//            your_score.setGravity(Gravity.LEFT);
//            your_score.setTextColor(Color.RED);
//            your_score.setLayoutParams(frame_score);
//            frameLayout2.addView(your_score);
//
//
//        }
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    fly();
//                   // bird.setY(bird.getY() + y_motion);
//                  //  bird.setImageResource(R.drawable.bird2);
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                    // fly();
//                    // bird.setImageResource(R.drawable.bird1);
//
//                    break;
//            }
//            bird.setImageResource(R.drawable.bird1);
//
//         return super.onTouchEvent(event);
//    }
//
//    class MyThread extends Thread {
//        @Override
//        public void run() {
//            for(int i =0; i<100;i++){
//                Message message = Message.obtain();
//                message.arg1 = i;
//                handler.sendMessage(message);
//               try {
//                    Thread.sleep(100);
//                   // addStuff = false;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//               }
//                if (i == 98){
//                    i =2;
//                }
//            }
//        }
//    }
//    class Gravity_Thread implements Runnable{
//        @Override
//        public void run() {
//            for(int i =0; i<100;i++){
//                Message message2 = Message.obtain();
//                handler2.sendMessage(message2);
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (i == 98){
//                    i =2;
//                }
//            }
//        }
//    }
//    class CollisionThread implements Runnable{
//        @Override
//        public void run() {
//            for(int i =0; i<100;i++){
//                Message message3 = Message.obtain();
//                collision_handler.sendMessage(message3);
//                try {
//                    Thread.sleep(75);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//
//                }
//                if (i == 98){
//                    i =2;
//                }
//            }
//        }
//    }
//    ImageView jetY,cloud1,cloud2,cloud3,jetB,ship;
//    public void addWave() {
//        //Wave
//        for (int i =0 ; i<7; i++){
//            ImageView waveStatic = new ImageView(this);
//            waveStatic.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpwaveStatic = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpwaveStatic.setMargins(0 + (400*i) -5, height - 170, 0, 0);
//            waveStatic.setLayoutParams(flpwaveStatic);
//            frameLayout2.addView(waveStatic);
//
//            ImageView wave = new ImageView(this);
//            wave.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave.setMargins((390*i)-400, height - 170, 0, 0);
//            wave.setLayoutParams(flpWave);
//            TranslateAnimation move_wave = new TranslateAnimation(0, 2*width+600, 0, 0);
//            move_wave.setDuration(4000);
//            move_wave.setRepeatCount(-1);
//           // move_wave.setRepeatMode(2);
//            wave.startAnimation(move_wave);
//            frameLayout2.addView(wave);
//
//            ImageView wave2 = new ImageView(this);
//            wave2.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave2 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave2.setMargins((400*i)-605-width, height - 170, 0, 0);
//            wave2.setLayoutParams(flpWave2);
//            TranslateAnimation move_wave2 = new TranslateAnimation(0, 600 + 2*width, 0, 0);
//            move_wave2.setDuration(4000);
//            move_wave2.setRepeatCount(-1);
//            wave2.startAnimation(move_wave2);
//            frameLayout2.addView(wave2);
//
//            ImageView wave3 = new ImageView(this);
//            wave3.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave3 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave2.setMargins((400*i)-605-width, height - 170, 0, 0);
//            wave3.setLayoutParams(flpWave2);
//            TranslateAnimation move_wave3 = new TranslateAnimation(0, 300 + width, 0, 0);
//            move_wave3.setDuration(2000);
//            move_wave3.setRepeatCount(-1);
//            wave3.startAnimation(move_wave3);
//            frameLayout2.addView(wave3);
//
//            ImageView wave4 = new ImageView(this);
//            wave4.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave4 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave4.setMargins((400*i)-605+width/3, height - 170, 0, 0);
//            wave4.setLayoutParams(flpWave4);
//            TranslateAnimation move_wave4 = new TranslateAnimation(0, 300 +width, 0, 0);
//            move_wave4.setDuration(2000);
//            move_wave4.setRepeatCount(-1);
//            wave4.startAnimation(move_wave4);
//            frameLayout2.addView(wave4);
//
//            ImageView wave_1 = new ImageView(this);
//            wave.setImageResource(R.drawable.wave2);
//            FrameLayout.LayoutParams flpWave_1 = new FrameLayout.LayoutParams(
//                    400, 200);
//            flpWave_1.setMargins((390*i)-400, height - 170, 0, 0);
//            wave_1.setLayoutParams(flpWave_1);
//            TranslateAnimation move_wave_1 = new TranslateAnimation(0, width+2000, 0, 0);
//            move_wave_1.setDuration(3000);
//            move_wave_1.setRepeatCount(-1);
//            wave_1.startAnimation(move_wave_1);
//            frameLayout2.addView(wave_1);
//        }
//
//    }
//    public void addJetY(){
//         jetY = new ImageView(this);
//        jetY.setImageResource(R.drawable.plane2);
//        Random random1 = new Random();
//        Random random12 = new Random();
//        int random11 = 100 + random1.nextInt(height-500);
//        int random112 = width + random12.nextInt(width/2);
//        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
//                200, 200);
//        fl_jet.setMargins(random112, random11, 0, 0);
//        jetY.setLayoutParams(fl_jet);
//        TranslateAnimation move_jetY = new TranslateAnimation(0, 0-(width*2)-300, 0, 0);
//        move_jetY.setDuration(4000);
//        move_jetY.setRepeatCount(0);
//        move_jetY.setRepeatMode(1);
//        jetY.setBackgroundColor(Color.BLACK);
//        jetY.startAnimation(move_jetY);
//        frameLayout2.addView(jetY);
//
//    }
//    public void addJetB(){
//        jetB = new ImageView(this);
//        jetB.setImageResource(R.drawable.plane1);
//        Random random1y = new Random();
//        Random random1x = new Random();
//        int random11y = 120 + random1y.nextInt(height -500);
//        int random11x = width + random1x.nextInt(600);
//        FrameLayout.LayoutParams fl_jet = new FrameLayout.LayoutParams(
//                200, 200);
//        fl_jet.setMargins(random11x, random11y, 0, 0);
//        jetB.setLayoutParams(fl_jet);
//        TranslateAnimation move_jetB = new TranslateAnimation(0, 0-width*2, 0, 0);
//        move_jetB.setDuration(5000);
//        move_jetB.setRepeatCount(0);
//        move_jetB.setRepeatMode(1);
//        jetB.setBackgroundColor(Color.BLACK);
//        jetB.startAnimation(move_jetB);
//        frameLayout2.addView(jetB);
//    }
//    public void addCloud1(){
//         cloud1 = new ImageView(this);
//        cloud1.setImageResource(R.drawable.cloud1);
//        Random random1y = new Random();
//        Random random1x = new Random();
//        int random11x = width + random1x.nextInt(width);
//        int random11y = random1x.nextInt(width/12);
//        FrameLayout.LayoutParams fl_cloud = new FrameLayout.LayoutParams(
//                292*3/2, 162*3/2);
//        fl_cloud.setMargins(random11x, random11y, 0, 0);
//        cloud1.setLayoutParams(fl_cloud);
//        TranslateAnimation move_cloud = new TranslateAnimation(0, 0-width*3, 0, 0);
//        move_cloud.setDuration(8000);
//        move_cloud.setRepeatCount(0);
//        move_cloud.setRepeatMode(1);
//        cloud1.startAnimation(move_cloud);
//        frameLayout2.addView(cloud1);
//    }
//    public void addCloud2(){
//        cloud2 = new ImageView(this);
//        cloud2.setImageResource(R.drawable.cloud2);
//        Random random1y = new Random();
//        Random random1x = new Random();
//        int random11x = width*3/2 + random1x.nextInt(width);
//        int random11y = random1y.nextInt(100-10) + 10;
//        FrameLayout.LayoutParams fl_cloud2 = new FrameLayout.LayoutParams(
//                298*3/2, 158*3/2);
//        fl_cloud2.setMargins(random11x, random11y, 0, 0);
//        cloud2.setLayoutParams(fl_cloud2);
//        TranslateAnimation move_cloud2 = new TranslateAnimation(0, 0-width*3, 0, 0);
//        move_cloud2.setDuration(8000);
//        move_cloud2.setRepeatCount(0);
//        move_cloud2.setRepeatMode(1);
//        cloud2.startAnimation(move_cloud2);
//        frameLayout2.addView(cloud2);
//    }
//    public void addCloud3(){
//        cloud3 = new ImageView(this);
//        cloud3.setImageResource(R.drawable.cloud3);
//        Random random1y = new Random();
//        Random random1x = new Random();
//        int random11x = 2*width + random1x.nextInt(width/2);
//        int random11y = random1y.nextInt(100-10) + 10;
//        FrameLayout.LayoutParams fl_cloud3 = new FrameLayout.LayoutParams(
//                244*3/2, 126*3/2);
//        fl_cloud3.setMargins(random11x, random11y, 0, 0);
//        cloud3.setLayoutParams(fl_cloud3);
//        TranslateAnimation move_cloud3 = new TranslateAnimation(0, 0-width*3 +500, 0, 0);
//        move_cloud3.setDuration(12000);
//        move_cloud3.setRepeatCount(0);
//        move_cloud3.setRepeatMode(1);
//        cloud3.startAnimation(move_cloud3);
//        frameLayout2.addView(cloud3);
//    }
//    public void addShip(){
//        ship = new ImageView(this);
//        ship.setImageResource(R.drawable.ship2);
//        Random random1 = new Random();
//        int ramdom11 = height-550 + random1.nextInt(10);
//        FrameLayout.LayoutParams fl_ship = new FrameLayout.LayoutParams(
//                531, 386);
//        fl_ship.setMargins(0 - 1000, ramdom11, 0, 0);
//        ship.setLayoutParams(fl_ship);
//        TranslateAnimation move_ship = new TranslateAnimation(0, width+1000, 0, 0);
//        move_ship.setDuration(50000);
//        ship.startAnimation(move_ship);
//        frameLayout2.addView(ship);
//    }
//    public void fly(){
//        if(started != true){
//            started = true;
//        }
//            y_motion = -80;
//        bird.setY(bird.getY() + y_motion);
//        }
//    public void gravity(){
//        if(started == true) {
//            //TranslateAnimation birdmove = new TranslateAnimation(0,0,0,200);
//            //birdmove.setDuration(50);
//           // birdmove.setRepeatMode(0);
//
//           // bird.startAnimation(birdmove);
//           bird.setY(bird.getY() + 15);
//        }
//    }
//
//    public void checkCollision(){
//
//        if (started == true) {
//           // bird_rect.set((Math.round(bird.getX()) - 100),(Math.round(bird.getY()) - 100),(Math.round(bird.getX()) + 100),(Math.round(bird.getY()) + 100));
//            Rect a  = new Rect(Math.round(bird.getX()), Math.round(bird.getY()), Math.round(bird.getX()) + 200, Math.round(bird.getY()) + 200);
//            //jet_b_rect.set(Math.round(jetB.getX())-100, Math.round(jetB.getY())-100, Math.round(jetB.getX()) + 100, Math.round(jetB.getY()) + 100);
//            Rect b = new Rect(Math.round(jetB.getX()), Math.round(jetB.getY()), Math.round(jetB.getX()) + 200, Math.round(jetB.getY()) + 200);
//            FrameLayout.LayoutParams rect_frame = new FrameLayout.LayoutParams(
//                    200, 200);
//            rect_frame.setMargins(Math.round(jetB.getX()), Math.round(jetB.getY()), Math.round(jetB.getX()) + 200, Math.round(jetB.getY()) + 200);
//            cloud1 = new ImageView(this);
//            cloud1.setImageResource(R.drawable.wave2);
//            cloud1.setLayoutParams(rect_frame);
//            frameLayout2.addView(cloud1);
//            //  s(random11x, random11y, 0, 0);
//            // jet_y_rect = new Rect(Math.round(jetY.getX()), Math.round(jetY.getY()), Math.round(jetY.getX()) + 200, Math.round(jetY.getY()) + 200);
//            //  frameLayout2.addView(bird_rect);
//            if ((bird.getY() > (height - 300)) || (bird.getY() < (0))) {
//                jetB.setBackgroundColor(Color.CYAN);
//               gameOver = true;
//            }
//           else {
//                jetB.setBackgroundColor(Color.BLUE);
//            }
//
//
//            if(a.intersect(b)){
//                jetY.setBackgroundColor(Color.RED);
//            }
//        }
//   }


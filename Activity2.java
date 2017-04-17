package com.vogelplay.vogel3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.google.android.gms.*;
/**
 * Created by maharjan on 3/9/17.
 */

public class Activity2 extends AppCompatActivity {
    boolean started;
    DrawView drawView;
    FrameLayout frameLayout, frameLayout2;
    int width, height,sc;
    int scoreInt=0;
    int highScore=0;
    Typeface face;
    Paint rectPaint;
    TextView tap,high_score,score, coconutScore,pineappleScore,flyScore;
    ImageView birdImage,ship,jetY,jetP,cloud1,cloud2,cloud3,tree,fly,p_tree;
    FrameLayout.LayoutParams flpBird,fl_jetY,fl_jetP,fl_cloud1,fl_cloud2,fl_cloud3,fl_ship,fl_tree,fl_fly,fl_pTree;
    Boolean avoidGravity;
    Boolean collision_jets = false;
    Boolean collision_clouds = false;
    Boolean collision_shark = false;
    Boolean collision_tree = false;
    Boolean collision_p_tree = false;
    Boolean collision_fly = false;
    Boolean addScore = true;
    Boolean gameOver = false;
    SharedPreferences prefs;
    MediaPlayer gameover_sound,loop2,score_sound;
    public  Activity2(){
        super();
    }

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
 //ADD SOUND LOOP 2
        loop2 = MediaPlayer.create(this,R.raw.intro2);
        loop2.setLooping(true);
        loop2.start();
        score_sound =  MediaPlayer.create(this,R.raw.coconut);
        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highScore = prefs.getInt("score", 0);
        addTextT2S();
        addTextHighScore();
        addWave();
        addSun();
        addBird();
       // Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, 1337);


    }
    public class DrawView extends View {
        public DrawView(Context context) {
            super(context);
            rectPaint = new Paint();
            rectPaint.setColor(Color.BLACK);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(3);
            bird_rect = new Rect(width / 3, height / 2, width / 3 + width/30, height / 2 + height/35);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (started == true && gameOver == false) {
                canvas.drawRect(bird_rect, rectPaint);
                canvas.drawRect(rectJetYellow,rectPaint);
                canvas.drawRect(rectJetPurple,rectPaint);
                canvas.drawRect(rectCloud1,rectPaint);
                canvas.drawRect(rectCloud2,rectPaint);
                canvas.drawRect(rectCloud3,rectPaint);
                canvas.drawRect(rectShip,rectPaint);
                canvas.drawRect(rectTree,rectPaint);
                canvas.drawRect(rectFly,rectPaint);
                canvas.drawRect(rectP_tree,rectPaint);
                moveJet();
                moveCloud();
                moveShip();
                gravity();
                moveTree();
                moveFly();
                moveP_tree();
                gameOverCollision();
            }
            if(addScore == true){
                scoreCollision();
                scoreSelection();
            }
            if(gameOver == true){
                gameOverCause();
                gameOver = false;
                started = false;
            }
            if (highScore < scoreInt){
                highScore = scoreInt;
            }
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(gameOver != true) {
                fly();
            }
            if (started != true) {
                started = true;
                frameLayout.removeView(tap);
                addTextScore();
                frameLayout.removeView(high_score);
                addTextHighScore();
                rectJetYellow = jetRect();
                rectJetPurple = jetRect();
                rectCloud1 = cloudRect();
                rectCloud2 = cloudRect();
                rectCloud3 = cloudRect();
                rectShip = shipRect();
                rectTree = treeRect();
                rectFly = flyRect();
                rectP_tree = p_treeRect();
                addJetYellow(rectJetYellow);
                addJetPurple(rectJetPurple);
                addCloud1(rectCloud1);
                addCloud2(rectCloud2);
                addCloud3(rectCloud3);
                addShip(rectShip);
                addTree(rectTree);
                addFly(rectFly);
                addP_tree(rectP_tree);
            }
            if(started == true){
                sc = scoreInt;
                String score_sting = "Score: \n  "+ sc;
                score.setText(score_sting);
                if(highScore<scoreInt){
                    highScore = scoreInt;
                    prefs.edit().putInt("score", highScore).commit();
                    String high_score_sting = "High Score: \n "+ highScore;
                    high_score.setText(high_score_sting);
                }
            }
            frameLayout.removeView(flyScore);
            frameLayout.removeView(coconutScore);
            frameLayout.removeView(pineappleScore );
            return super.onTouchEvent(event);
        }
    }
    int i = 0;
    int agCount =0;
    public void gravity() {
        if (avoidGravity == true){
            agCount = agCount +1;
            if(agCount == 10) {
                avoidGravity = false;
                agCount = 0;
            }
        }else {
            bird_rect.top += i / 2;
            bird_rect.bottom += i / 2;
            flpBird.setMargins(bird_rect.left - 30, bird_rect.top - 50, bird_rect.right, bird_rect.bottom);
            birdImage.setImageResource(R.drawable.bird1);
            birdImage.setLayoutParams(flpBird);
            i += 1;
        }
    }
    public void scoreCollision(){
        Rect br = bird_rect;
        Rect rf = rectFly;
        Rect rt = rectTree;
        Rect rpt = rectP_tree;
        if (CollisionTest(br,rf)){
            collision_fly = true;
        }
        if(CollisionTest(br,rt)){
            collision_tree = true;
        }
        if(CollisionTest(br,rpt)){
            collision_p_tree = true;
        }
    }
    public void scoreSelection(){
        if ( collision_fly == true){
            collision_fly = false;
            scoreInt +=50;
            addflyscore();
            addScore = false;
            moveFly2();
        }
        if(collision_tree == true){
            collision_tree = false;
            scoreInt += 500;
            addCoconut();
            addScore = false;
            score_sound.start();
        }if(collision_p_tree == true){
            collision_p_tree = false;
            scoreInt += 1000;
            addPineapple();
            addScore = false;
            score_sound.start();
        }
    }
    public void gameOverCollision() {
        Rect br = bird_rect;
        Rect rjy = rectJetYellow;
        Rect rjp = rectJetPurple;
        Rect rc1 = rectCloud1;
        Rect rc2 = rectCloud2;
        Rect rc3 = rectCloud3;
        Rect rs = rectShip;
        if (br.intersect(rjy) || (br.intersect(rjp))) {
            collision_jets = true;
            gameOver = true;
        }
        if((br.intersect(rc1)||(br.intersect(rc2))||(br.intersect(rc3)))){
            collision_clouds = true;
            gameOver = true;
        }
        if (bird_rect.bottom > 86*height/90){
            collision_shark = true;
            gameOver = true;
        }
        if(br.intersects(rs.left,rs.top,rs.right,rs.bottom)){
            fly();
            avoidGravity = true;
        }
        if (bird_rect.top < 0) {
            bird_rect.bottom += height/20;
            bird_rect.top +=height/20;
        }
    }
    public void gameOverCause(){
        if(collision_jets == true){
            addBang();
            gameover_sound = MediaPlayer.create(this,R.raw.blast);
            gameover_sound.start();
            collision_jets = false;
        }if(collision_clouds == true){
            addThunder();
            gameover_sound = MediaPlayer.create(this,R.raw.sound_electric);
            gameover_sound.start();
            collision_clouds = false;
        }else if(collision_shark == true){
            addShark();
            gameover_sound = MediaPlayer.create(this,R.raw.shark);
            gameover_sound.start();
            collision_shark = false;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i2 = new Intent(Activity2.this,Activity3.class);
                i2.putExtra("final_score", scoreInt);
                startActivity(i2);
            }
        }, 500);

    }

    @Override
    protected void onPause() {
        super.onPause();
        loop2.stop();
        loop2.release();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loop2 = MediaPlayer.create(this,R.raw.intro2);
        loop2.setLooping(true);
        loop2.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity2.this, MainActivity.class);
        startActivity(i);
    }

    public void fly() {
        i = 0;
        addScore = true;
        avoidGravity = true;
        scoreInt += 10;
        bird_rect.top -= height/8;
        bird_rect.bottom -= height/8;
        birdImage.setImageResource(R.drawable.bird2);
        flpBird.setMargins(bird_rect.left - 30, bird_rect.top - 50, bird_rect.right, bird_rect.bottom);
        birdImage.setLayoutParams(flpBird);

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    Rect bird_rect;
    Rect rectJetYellow = jetRect();
    Rect rectJetPurple = jetRect();
    Rect rectCloud1 = cloudRect();
    Rect rectCloud2 = cloudRect();
    Rect rectCloud3 = cloudRect();
    Rect rectShip = shipRect();
    Rect rectTree = treeRect();
    Rect rectFly = flyRect();
    Rect rectP_tree = p_treeRect();
    public void addTextT2S(){
        tap = new TextView(this);
        tap.setTypeface(face);
        tap.setText("Tap To Start");
        tap.setGravity(Gravity.CENTER);
        tap.setTextColor(Color.YELLOW);
        tap.setTextSize(55);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width*2/3,height/3);
        flpTtoS.setMargins(width/6,height/3,0,0);
        tap.setLayoutParams(flpTtoS);
        frameLayout.addView(tap);
    }
    public void addTextHighScore() {
        high_score = new TextView(this);
        high_score.setTypeface(face);
        high_score.setGravity(Gravity.CENTER);
        high_score.setTextColor(Color.RED);
        high_score.setTextSize(22);
        highScore = prefs.getInt("score", 0);

        if (highScore < scoreInt){
            highScore = scoreInt;
            prefs.edit().putInt("score", highScore).commit();
           // Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, 1337);
        }
        String high_score_sting = "High Score: "+ highScore;
        high_score.setText(high_score_sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width/2,height/5);
        flpTtoS.setMargins(width/4,height/6,0,0);
        high_score.setLayoutParams(flpTtoS);

        if(started == true){
            String high_score_sting2 = "High Score: \n"+ highScore;
            high_score.setText(high_score_sting2);
            high_score.setTextColor(Color.BLUE);
            high_score.setTextSize(9);
            high_score.setGravity(Gravity.LEFT);
            flpTtoS.setMargins(width/40,height/40,0,0);
            high_score.setLayoutParams(flpTtoS);
        }

        frameLayout.addView(high_score);
    }
    public void addTextScore(){
        score = new TextView(this);
        score.setTypeface(face);
        score.setGravity(Gravity.LEFT);
        score.setTextColor(Color.BLUE);
        score.setTextSize(10);
        sc = scoreInt;
        String score_sting = "Score: \n  "+ sc;
        score.setText(score_sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width/3,height/5);
        flpTtoS.setMargins(width/40,height/12,0,0);
        score.setLayoutParams(flpTtoS);
        frameLayout.addView(score);
    }
    public void addWave() {
        //Wave
        for (int i =0 ; i<7; i++){
            ImageView waveStatic = new ImageView(this);
            waveStatic.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpwaveStatic = new FrameLayout.LayoutParams(
                    width/5, height/5);
            flpwaveStatic.setMargins(0 + (width/5*i) -5, 8*height/9, 0, 0);
            waveStatic.setLayoutParams(flpwaveStatic);
            frameLayout2.addView(waveStatic);

            ImageView wave = new ImageView(this);
            wave.setImageResource(R.drawable.wave2);
            FrameLayout.LayoutParams flpWave = new FrameLayout.LayoutParams(
                    width/5, height/5);
            flpWave.setMargins((width/5*i)-2*width/5, 8*height/9, 0, 0);
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
                    width/5, height/5);
            flpWave2.setMargins((width/5*i)-3*width/5, 8*height/9, 0, 0);
            wave2.setLayoutParams(flpWave2);
            TranslateAnimation move_wave2 = new TranslateAnimation(0, 600, 0, 0);
            move_wave2.setInterpolator(new LinearInterpolator());
            move_wave2.setDuration(900);
            move_wave2.setRepeatCount(-1);
            wave2.startAnimation(move_wave2);
            frameLayout.addView(wave2);
        }

    }
    public Rect shipRect() {
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(100);
        int randomYY =  height*71/100 +randomX1.nextInt(80);
        int rectL =  -500- randomXX;
        int rectT= randomYY;
        Rect shipPurp =new Rect( rectL, rectT,rectL + width*32/100, rectT + height/10);
        return shipPurp;
    }
    public void addShip(Rect rect) {
        frameLayout2.removeView(ship);
        fl_ship = new FrameLayout.LayoutParams(
                width/2, height/2);
        fl_ship.setMargins(rect.left-width/17, rect.top-height/5, rect.left + 300, rect.top + 300);
        ship = new ImageView(this);
        ship.setImageResource(R.drawable.ship2);
        ship.setLayoutParams(fl_ship);
        frameLayout2.addView(ship);
    }
    public void moveShip() {
        rectShip.left += 1;
        rectShip.right += 1;
        fl_ship.setMargins(rectShip.left-width/17 , rectShip.top - height/5 , rectShip.left + 300, rectShip.top + 300);
        ship.setLayoutParams(fl_ship);
        if (rectShip.left > width) {
            rectShip = shipRect();
        }
    }
    public void addSun(){
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
    public void addBang(){
        FrameLayout.LayoutParams flpBang = new FrameLayout.LayoutParams(
                width/2,width/2);
        flpBang.setMargins(bird_rect.left-width/4 , bird_rect.top-width/4,0,0);
        ImageView bangImage = new ImageView(this);
        bangImage.setImageResource(R.drawable.crash);
        bangImage.setLayoutParams(flpBang);
        frameLayout.addView(bangImage);
        RotateAnimation rotate_bang = new RotateAnimation(0.0f, -260f, width/4,width/4,width/4,width/4);
        rotate_bang.setDuration(100);
        rotate_bang.setRepeatCount(2);
        rotate_bang.setInterpolator(new LinearInterpolator());
        bangImage.startAnimation(rotate_bang);
    }
    public void addThunder(){
        FrameLayout.LayoutParams flpThunder = new FrameLayout.LayoutParams(
                width/6,width/6);
        flpThunder.setMargins(bird_rect.left -width/12 , bird_rect.top-width/12,0,0);
        ImageView thunderImage = new ImageView(this);
        thunderImage.setImageResource(R.drawable.lightning);
        thunderImage.setLayoutParams(flpThunder);
        frameLayout.addView(thunderImage);
    }
    public void addShark(){
        FrameLayout.LayoutParams flpShark = new FrameLayout.LayoutParams(
                width/6,width/6);
        flpShark.setMargins(bird_rect.left -width/6 , height,0,0);
        ImageView sharkImage = new ImageView(this);
        sharkImage.setImageResource(R.drawable.shark);
        sharkImage.setLayoutParams(flpShark);
        frameLayout.addView(sharkImage);
        TranslateAnimation move_shark = new TranslateAnimation(0, width/12, 0, -height/7);
        //move_shark.setInterpolator(new LinearInterpolator());
        move_shark.setDuration(700);
        move_shark.setRepeatCount(1);
        sharkImage.startAnimation(move_shark);
    }
    public void addBird(){
        flpBird = new FrameLayout.LayoutParams(
                width/12,width/12);
        flpBird.setMargins(bird_rect.left -30, bird_rect.top-50,bird_rect.right-75,bird_rect.bottom-75);
        birdImage = new ImageView(this);
        birdImage.setImageResource(R.drawable.bird1);
        birdImage.setLayoutParams(flpBird);
        frameLayout2.addView(birdImage);
    }
    public Rect jetRect() {
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(1000);
        int randomYY = randomX1.nextInt(1000);
        int rectL = width + randomXX;
        int rectT= height/20+randomYY;
        Rect jetPurp =new Rect( rectL, rectT,rectL + width/11 , rectT + width/34);
        return jetPurp;
    }
    public void addJetYellow(Rect rect) {
        frameLayout2.removeView(jetY);
        fl_jetY = new FrameLayout.LayoutParams(
                width/6, width/9);
        fl_jetY.setMargins(rect.left-75, rect.top+100, rect.left + 200, rect.top + 200);
        jetY = new ImageView(this);
        jetY.setImageResource(R.drawable.plane2);
        jetY.setLayoutParams(fl_jetY);
        frameLayout2.addView(jetY);
    }
    public void addJetPurple(Rect rect) {
        frameLayout2.removeView(jetP);
        fl_jetP = new FrameLayout.LayoutParams(
                width/6, width/9);
        fl_jetP.setMargins(rect.left-75, rect.top+100, rect.left + 200, rect.top + 200);
        jetP = new ImageView(this);
        jetP.setImageResource(R.drawable.plane1);
        jetP.setLayoutParams(fl_jetP);
        frameLayout2.addView(jetP);
    }
    public void moveJet(){
        //JET YELLOW MOTION
        rectJetYellow.left -= 15;
        rectJetYellow.right -=15;
        fl_jetY.setMargins(rectJetYellow.left-75, rectJetYellow.top-75, rectJetYellow.left + 200, rectJetYellow.top + 200);
        jetY.setLayoutParams(fl_jetY);
        if (rectJetYellow.right < -100) {
            rectJetYellow = jetRect();
        }
        //JET PURPLE MOTION
        rectJetPurple.left -= 20;
        rectJetPurple.right -=20;
        fl_jetP.setMargins(rectJetPurple.left-75, rectJetPurple.top-75, rectJetPurple.left + 200, rectJetPurple.top + 200);
        jetP.setLayoutParams(fl_jetP);
        if (rectJetPurple.right < -100) {
            rectJetPurple = jetRect();
        }
    }
    public Rect cloudRect() {
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(200);
        int randomYY = 5+randomX1.nextInt(200);
        int rectL = width + randomXX;
        int rectT= randomYY;
        Rect cloudrect =new Rect( rectL, rectT,rectL + width/8 , rectT + width/26);
        return cloudrect;
    }
    public void addCloud1(Rect rect) {
        frameLayout2.removeView(cloud1);
        fl_cloud1 = new FrameLayout.LayoutParams(
                width/5, width/6);
        fl_cloud1.setMargins(rect.left-width/25, rect.top-width/25, rect.left + width/6, rect.top + width/6);
        cloud1 = new ImageView(this);
        cloud1.setImageResource(R.drawable.cloud1);
        cloud1.setLayoutParams(fl_cloud1);
        frameLayout2.addView(cloud1);
    }
    public void addCloud2(Rect rect) {
        frameLayout2.removeView(cloud2);
        fl_cloud2 = new FrameLayout.LayoutParams(
                width/5, width/6);
        fl_cloud2.setMargins(rect.left-width/25, rect.top-width/25, rect.left + width/6, rect.top + width/6);
        cloud2 = new ImageView(this);
        cloud2.setImageResource(R.drawable.cloud2);
        cloud2.setLayoutParams(fl_cloud2);
        frameLayout2.addView(cloud2);
    }
    public void addCloud3(Rect rect) {
        frameLayout2.removeView(cloud3);
        fl_cloud3 = new FrameLayout.LayoutParams(
                width/5, width/7);
        fl_cloud3.setMargins(rect.left-width/25, rect.top-width/25, rect.left + width/5, rect.top + width/5);
        cloud3 = new ImageView(this);
        cloud3.setImageResource(R.drawable.cloud3);
        cloud3.setLayoutParams(fl_cloud3);
        frameLayout2.addView(cloud3);
    }
    public void moveCloud() {
        rectCloud1.left -= 5;
        rectCloud1.right -= 5;
        fl_cloud1.setMargins(rectCloud1.left-width/25, rectCloud1.top-width/15, rectCloud1.left + 200, rectCloud1.top + 200);
        cloud1.setLayoutParams(fl_cloud1);
        if (rectCloud1.right < -100) {
            rectCloud1 = cloudRect();
        }
        rectCloud2.left -= 4;
        rectCloud2.right -= 4;
        fl_cloud2.setMargins(rectCloud2.left-width/25, rectCloud2.top-width/15, rectCloud2.left + 200, rectCloud2.top + 200);
        cloud2.setLayoutParams(fl_cloud2);
        if (rectCloud2.right < -100) {
            rectCloud2 = cloudRect();
        }
        rectCloud3.left -= 6;
        rectCloud3.right -= 6;
        fl_cloud3.setMargins(rectCloud3.left-width/25, rectCloud3.top-width/20, rectCloud3.left + 200, rectCloud3.top + 200);
        cloud3.setLayoutParams(fl_cloud3);
        if (rectCloud3.right < -100) {
            rectCloud3 = cloudRect();
        }
    }
    public Rect treeRect() {
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(100);
        int randomYY = height*74/100 + randomX1.nextInt(20);
        int rectL = width + randomXX;
        int rectT= randomYY;
        Rect jetPurp =new Rect(rectL, rectT,rectL + width/40, rectT + height/5);
        return jetPurp;
    }
    public void addTree(Rect rect) {
        frameLayout2.removeView(tree);
        fl_tree = new FrameLayout.LayoutParams(
                width/4, width/4);
        fl_tree.setMargins(rect.left-width/8, rect.top, rect.left + 200, rect.top + 200);
        tree = new ImageView(this);
        tree.setImageResource(R.drawable.tree);
        tree.setLayoutParams(fl_tree);
        frameLayout2.addView(tree);
    }
    public void moveTree() {
        rectTree.left -= 18;
        rectTree.right -= 18;
        fl_tree.setMargins(rectTree.left-width/9 , rectTree.top -width/15 , rectTree.left , rectTree.top + 200);
        tree.setLayoutParams(fl_tree);
        if (rectTree.left < -100) {
            rectTree = treeRect();
        }
    }
    public void addCoconut(){
        FrameLayout.LayoutParams flpCoconut = new FrameLayout.LayoutParams(
                width/20,width/20);
        flpCoconut.setMargins(bird_rect.left -width/60 , bird_rect.bottom,0,0);
        final ImageView coconutImage = new ImageView(this);
        coconutImage.setImageResource(R.drawable.fruit);
        coconutImage.setLayoutParams(flpCoconut);
        frameLayout.addView(coconutImage);
        TranslateAnimation move_coconut = new TranslateAnimation(0, width/20, 0, +height/3);
        move_coconut.setDuration(900);
        move_coconut.setFillAfter(true);
        move_coconut.setRepeatCount(0);
        coconutImage.startAnimation(move_coconut);
        coconutScore = new TextView(this);
        coconutScore.setTypeface(face);
        coconutScore.setGravity(Gravity.CENTER);
        coconutScore.setTextColor(Color.YELLOW);
        coconutScore.setTextSize(25);
        String sting = "+ 500";
        coconutScore.setText(sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width/4,height/4);
        flpTtoS.setMargins(width*3/8,height/6,0,0);
        coconutScore.setLayoutParams(flpTtoS);
        frameLayout.addView(coconutScore);

    }
    public void addPineapple(){
        FrameLayout.LayoutParams flpCoconut = new FrameLayout.LayoutParams(
                width/19,width/19);
        flpCoconut.setMargins(bird_rect.left -width/60 , bird_rect.bottom,0,0);
        final ImageView pineappleImage = new ImageView(this);
        pineappleImage.setImageResource(R.drawable.pineapple);
        pineappleImage.setLayoutParams(flpCoconut);
        frameLayout.addView(pineappleImage);
        TranslateAnimation move_coconut = new TranslateAnimation(0, width/20, 0, +height/3);
        move_coconut.setDuration(900);
        move_coconut.setFillAfter(true);
        move_coconut.setRepeatCount(0);
        pineappleImage.startAnimation(move_coconut);
         pineappleScore = new TextView(this);
        pineappleScore.setTypeface(face);
        pineappleScore.setGravity(Gravity.CENTER);
        pineappleScore.setTextColor(Color.YELLOW);
        pineappleScore.setTextSize(25);
        String sting = "+ 1000";
        pineappleScore.setText(sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width/4,height/4);
        flpTtoS.setMargins(width*3/8,height/6,0,0);
        pineappleScore.setLayoutParams(flpTtoS);
        frameLayout.addView(pineappleScore);
    }
    public void addflyscore(){
         flyScore = new TextView(this);
        flyScore.setTypeface(face);
        flyScore.setGravity(Gravity.CENTER);
        flyScore.setTextColor(Color.YELLOW);
        flyScore.setTextSize(25);
        String sting = "+ 50";
        flyScore.setText(sting);
        FrameLayout.LayoutParams flpTtoS = new FrameLayout.LayoutParams(
                width/4,height/4);
        flpTtoS.setMargins(width*3/8,height/6,0,0);
        flyScore.setLayoutParams(flpTtoS);
        frameLayout.addView(flyScore);

    }
    public Rect p_treeRect() {
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(500);
        int rectL = width + randomXX;
        int randomYY = height*87/100 + randomX1.nextInt(20);
        int rectT= randomYY;
        Rect jetPurp =new Rect(rectL, rectT,rectL + width/13, rectT + height/34);
        return jetPurp;
    }
    public void addP_tree(Rect rect) {
        frameLayout2.removeView(p_tree);
        fl_pTree = new FrameLayout.LayoutParams(
                width/5, height/5);
        fl_pTree.setMargins(rect.left-width/20, rect.top-height/30, rect.left + 20, rect.top + 20);///cant change here
        p_tree = new ImageView(this);
        p_tree.setImageResource(R.drawable.pineappletree);
        p_tree.setLayoutParams(fl_pTree);
        frameLayout2.addView(p_tree);
    }
    public void moveP_tree() {
        rectP_tree.left -= 1;
        rectP_tree.right -= 1;
        fl_pTree.setMargins(rectP_tree.left-width/20 , rectP_tree.top -height/15, rectP_tree.left + 20, rectP_tree.top + 20);
        p_tree.setLayoutParams(fl_pTree);
        if (rectP_tree.left < -100) {
            rectP_tree = p_treeRect();
        }
    }
    public Rect flyRect(){
        Random randomX1 = new Random();
        int randomXX= width + randomX1.nextInt(600);
        int randomYY = 200 + randomX1.nextInt(900);
        int rectL =  randomXX;
        int rectT= randomYY;
        Rect jetPurp =new Rect(rectL, rectT,rectL + width/30, rectT + height/38);
        return jetPurp;
    }
    public void addFly(Rect rect) {
        frameLayout2.removeView(fly);
        fl_fly = new FrameLayout.LayoutParams(
                width/16, height/15);
        fl_fly.setMargins(rect.left-width/66, rect.top-height/80, rect.left + 20, rect.top + 20);
        fly = new ImageView(this);
        fly.setImageResource(R.drawable.fly2);
        fly.setLayoutParams(fl_fly);
        frameLayout2.addView(fly);
        TranslateAnimation fly_animation = new TranslateAnimation(0,0,0,35);
        fly_animation.setRepeatCount(-1);
        fly_animation.setRepeatMode(1);
        fly_animation.setDuration(200);
        fly_animation.setFillAfter(true);
        fly.startAnimation(fly_animation);
    }
    public void moveFly() {
        rectFly.left -= 9;
        rectFly.right -= 9;
        fl_fly.setMargins(rectFly.left-width/36 , rectFly.top -height/30 , rectFly.left + 20, rectFly.top + 20);
        fly.setLayoutParams(fl_fly);
        if (rectFly.left < -100) {
            rectFly = flyRect();
        }
    }
    public void moveFly2(){
        Random randomX1 = new Random();
        int randomXX= randomX1.nextInt(width/2);
        RotateAnimation rotate_fly = new RotateAnimation(0.0f, -360f, width/26,width/26);
        rotate_fly.setDuration(200);
        rotate_fly.setRepeatCount(-1);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotate_fly);
        //fly.startAnimation(rotate_fly);
        if(bird_rect.top>rectFly.top){
            TranslateAnimation fly_animation2 = new TranslateAnimation(0,randomXX,0,-width);
            fly_animation2.setRepeatCount(0);
            fly_animation2.setDuration(2000);
            fly_animation2.setFillAfter(true);
            animationSet.addAnimation(fly_animation2);
            fly.startAnimation(animationSet);
        }else{
            TranslateAnimation fly_animation2 = new TranslateAnimation(0,randomXX,0,width);
            fly_animation2.setRepeatCount(0);
            fly_animation2.setDuration(2000);
            fly_animation2.setFillAfter(true);
            animationSet.addAnimation(fly_animation2);
            fly.startAnimation(animationSet);
    }   rectFly.left = -200;
        rectFly.right=-200;
        addFly(rectFly);
    }
    public boolean CollisionTest(Rect one, Rect two) {
        return one.left <= two.right && one.right >= two.left &&
                one.top <= two.bottom && one.bottom >= two.top;
    }

}

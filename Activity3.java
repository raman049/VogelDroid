package com.vogelplay.vogel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by maharjan on 3/11/17.
 */

public class Activity3 extends AppCompatActivity {

    Rect bird;
    int canvas_width;
    int canvas_height;
    int bird_x;
    int bird_y;

    //View view;
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

    }

    public class MyView extends View {
        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            canvas_width = getWidth();
            canvas_height = getHeight();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(255,153,204,255));  //background color
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);


            Bitmap image1 ;
            bird_x = canvas_width/3;
            bird_y = canvas_height/2;
            image1 = BitmapFactory.decodeResource(getResources(),R.drawable.wave2);
            image1 = Bitmap.createScaledBitmap(image1,image1.getWidth()/3,image1.getHeight()/3,true);
            canvas.drawBitmap(image1,300,600,null);

            Bitmap birdImage;
            birdImage = BitmapFactory.decodeResource(getResources(),R.drawable.bird1);
            birdImage = Bitmap.createScaledBitmap(birdImage,birdImage.getWidth()/3,birdImage.getHeight()/3,true);
            canvas.drawBitmap(birdImage,bird_x,bird_y,null);

        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            bird_x = bird_x + 5;
            bird_y = bird_y + 5;
            return super.onTouchEvent(event);
        }
    }
}

package edu.coe.shughes.movingdots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Ian on 3/25/2017.
 */

public class BonusView extends View {

    private int x;
    private int y;
    private int r = 50;
    private Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int speed = 10;
    private int delay = 25;

    private int timeAlive = 0;

    private boolean hit = false;

    private Handler refreshHandler = new Handler();


    private Canvas canvas = new Canvas();

    public BonusView(Context context, int x1, int y1) {
        super(context);

        x = x1;
        y = y1;

        setX(x);
        setY(y);

        //Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawCircle(x, y, r, p);

        refreshHandler.post(update);
    }

    public void setHit(boolean hit1) {hit = hit1;}

    private Runnable update = new Runnable() {
        @Override
        public void run() {

            if (hit){
                setAlpha(0);
                Thread.interrupted();
            }
            else{
                x = x+speed;
                setX(x);
                setY(y);

                invalidate();
                refreshHandler.postDelayed(update, delay);
                timeAlive = timeAlive + delay;

                if (timeAlive>3000){
                    hit = true;
                }
            }

        }
    };

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, p);
    }
}

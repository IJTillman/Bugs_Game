package edu.coe.shughes.movingdots;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by SHughes on 3/2/2017.
 */

public class BugView extends ImageView {

    public static final int REDBUG = 1;
    public static final int BLUEBUG = 0;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private int x;
    private int y;
    private Random rand;

    private Random rand1;
    private Random rand2;

    private int speed;
    private float direction;
    private double dirRad;
    private int size;

    private Handler refreshHandler = new Handler();
    private long delay = 50;

    private boolean squished;

    private int timeDead;
    private boolean dead;
    private float opacity;

    public BugView(Context context) {
        super(context);
        init(1);

    }

    public BugView(Context context, int i) {
        super(context);
        init(i);

    }

    public BugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(1);
    }

    public BugView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(1);
    }

    private void init(int i){
        rand = new Random();

        rand1 = new Random();
        rand2 = new Random();


        maxY= 500;
        maxX = 1000;
        minX = 0;
        minY= 0;
        x = rand1.nextInt(maxX);
        y = rand2.nextInt(maxY);
        size = 150;

        squished = false;

        // Adjust the size of the bug
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        setLayoutParams(layoutParams);

        speed = 10;
        direction = 180;
        dirRad = direction * Math.PI /180;
        setRotation(direction);
        opacity = 1f;
        dead = false;

        if (i == 1) {
            setImageResource(R.drawable.redbug);
        }
        else{
            setImageResource(R.drawable.bluebug);
        }
        refreshHandler.post(update);
    }

    public void setBugType(int t){
        if (t == BLUEBUG){
            setImageResource(R.drawable.bluebug);
        }
        else{
            setImageResource(R.drawable.redbug);
        }
    }

    public void squished(int i){
        if (i==0){
            setImageResource(R.mipmap.blue);
            squished = true;
        }
        else{
            setImageResource(R.mipmap.red);
            squished = true;
        }
    }

    public boolean getSquished(){
        return squished;
    }



    private Runnable update = new Runnable() {
        @Override
        public void run() {
            if (!squished) {
                int newx;
                int newy;

                // Is the bug going to turn?
                if (rand.nextInt(30) < 2) {
                    direction += rand.nextInt(90) - 45;
                    dirRad = direction * Math.PI / 180;
                    setRotation(direction);
                }

                // Where will the bug go.
                newx = x + (int) (speed * Math.sin(dirRad));
                newy = y - (int) (speed * Math.cos(dirRad));

                // Did you hit a boundary?
                if ((newx > maxX - 10) || (newx < minX + 10) || (newy > maxY - 10) || (newy < minY - 10)) {
                    direction = (direction + 180) % 360;
                    dirRad = direction * Math.PI / 180;
                    setRotation(direction);
                } else {
                    x = newx;
                    y = newy;
                    setX(x);
                    setY(y);
                }

                //Log.d("BUGS", ((Integer)x).toString() +  " "+ ((Integer)x).toString());

                invalidate();
                refreshHandler.postDelayed(update, delay);
            }

            else{
                timeDead = timeDead + 50;
                if (timeDead>500) {
                    opacity = opacity - 0.05f;
                    setAlpha(opacity);
                    if (timeDead > 1500) {
                        Thread.interrupted();
                        dead = true;
                    }
                }
                invalidate();
                refreshHandler.postDelayed(update, delay);

            }
        }
    };

}

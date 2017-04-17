package edu.coe.shughes.movingdots;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Ian on 3/23/2017.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class VersusView extends AppCompatActivity {


    private FrameLayout mFrameLayout;

    private int numBugs = 6;

    private BugView bug;

    private Random rand;
    private Random randBonus;

    private int randomX;
    private int randomY;

    private long delay = 1000;

    private Handler refreshHandler = new Handler();

    TextView redScore;
    TextView blueScore;

    private Speed global_speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_layout);
        idControls();
        initView();
    }

    public void idControls() {
        redScore = (TextView) this.findViewById(R.id.redScore);
        blueScore = (TextView) this.findViewById(R.id.blueScore);

        refreshHandler.post(update);
        randBonus = new Random();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initView() {

        mFrameLayout = (FrameLayout) findViewById(R.id.content);

        global_speed = new Speed();
        global_speed.setSpeed(15);

        for (int i = 0; i < numBugs; i++) {
            final BugView b = new BugView(this,0,global_speed);

            //b.setX(i * 20);
            //b.setY(40);
            b.setBugType(BugView.BLUEBUG);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!b.getSquished()) {
                        addBug(0);
                        blueScore.setText(String.valueOf(Integer.valueOf(blueScore.getText().toString()) + 1));
                    }
                    b.squished(0);
                    b.setElevation(0f);
                }
            });
            b.setElevation(1f);


            mFrameLayout.addView(b);
        }

        for (int i = 0; i < numBugs; i++) {
            final BugView b = new BugView(this,1,global_speed);
            //b.setX(i * 20);
            //b.setY(40);
            b.setBugType(BugView.REDBUG);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!b.getSquished()) {
                        addBug(1);
                        redScore.setText(String.valueOf(Integer.valueOf(redScore.getText().toString()) + 1));
                    }
                    b.squished(1);
                    b.setElevation(0f);
                }
            });
            b.setElevation(1f);

            mFrameLayout.addView(b);
        }

    }

    private void addBug(final int i) {
        final BugView b = new BugView(this,i,global_speed);
        rand = new Random(50);
        b.setX(3 * rand.nextInt() + 1);
        b.setY(2 * rand.nextInt() + 1);
        b.setBugType(i);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b.getSquished()) {
                    addBug(i);
                    if (i == 0) {
                        blueScore.setText(String.valueOf(Integer.valueOf(blueScore.getText().toString()) + 1));
                    } else {
                        redScore.setText(String.valueOf(Integer.valueOf(redScore.getText().toString()) + 1));
                    }
                }
                b.squished(i);
                b.setElevation(0f);


            }
        });
        b.setElevation(1f);

        mFrameLayout.addView(b);

    }

    private void bonus(){
        final BonusView bonus = new BonusView(VersusView.this,-20,200);

        bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonus.setHit(true);
                increaseSpeed();
                mFrameLayout.removeView(bonus);
            }
        });
        mFrameLayout.addView(bonus);
        bonus.invalidate();

        //Toast.makeText(VersusView.this, "HELLO", Toast.LENGTH_SHORT).show();

    }

    private void increaseSpeed(){
        global_speed.setSpeed(global_speed.getSpeed()+10);
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            randBonus = new Random();
            if (randBonus.nextInt(500)<20){
                bonus();
            }
            refreshHandler.postDelayed(update, delay);
        }
    };



}

package edu.coe.shughes.movingdots;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Ian on 4/11/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class SinglePlayer extends AppCompatActivity{
    private FrameLayout mFrameLayout;

    private int numBugs = 6;

    private BugView bug;

    private Random rand;
    private Random randBonus;

    private int randomX;
    private int randomY;

    private long delay = 1000;

    private Handler refreshHandler = new Handler();

    TextView txt_score;

    Button btn_exit;

    private Speed global_speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);
        idControls();
        initView();
    }

    public void idControls() {
        txt_score = (TextView) this.findViewById(R.id.score);

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

        mFrameLayout = (FrameLayout) findViewById(R.id.content2);

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
                        txt_score.setText(String.valueOf(Integer.valueOf(txt_score.getText().toString()) + 1));
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
                        txt_score.setText(String.valueOf(Integer.valueOf(txt_score.getText().toString()) + 1));
                    }
                    b.squished(1);
                    b.setElevation(0f);
                }
            });
            b.setElevation(1f);

            mFrameLayout.addView(b);
        }

        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    txt_score.setText(String.valueOf(Integer.valueOf(txt_score.getText().toString()) + 1));

                }
                b.squished(i);
                b.setElevation(0f);


            }
        });
        b.setElevation(1f);

        mFrameLayout.addView(b);

    }

    private void bonus(){
        final BonusView bonus = new BonusView(SinglePlayer.this, -20, 200);

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
            else{
                randBonus = new Random(200);
            }
            refreshHandler.postDelayed(update, delay);
        }
    };




}

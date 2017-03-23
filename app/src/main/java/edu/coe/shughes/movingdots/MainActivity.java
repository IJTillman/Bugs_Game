package edu.coe.shughes.movingdots;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    private int numBugs = 5;

    private Random rand;

    private int randomX;
    private int randomY;

    TextView redScore;
    TextView blueScore;
    int scores[] = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_layout);
        idControls();
        initView();
    }

    public void idControls(){
        redScore = (TextView) this.findViewById(R.id.redScore);
        blueScore = (TextView) this.findViewById(R.id.blueScore);
        scores[0]=0;
        scores[1]=1;
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


    private void initView(){

        mFrameLayout = (FrameLayout) findViewById(R.id.content);
        /*
        BugView b1 = new BugView(this);
        b1.setX(0);
        b1.setY(0);
        mFrameLayout.addView(b1);
        BugView b2 = new BugView(this);
        b2.setX(40);
        b2.setY(40);
        b2.setBugType(BugView.BLUEBUG);

        mFrameLayout.addView(b2);
        */

        for (int i = 0; i < numBugs; i ++){
            final BugView b = new BugView(this);
            b.setX(i * 20);
            b.setY(40);
            b.setBugType(BugView.BLUEBUG);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!b.getSquished()){
                        addBug(0);
                        blueScore.setText(String.valueOf(Integer.valueOf(blueScore.getText().toString())+1));
                    }
                    b.squished(0);
                    b.setElevation(0f);
                }
            });
            b.setElevation(1f);


            mFrameLayout.addView(b);
        }

        for (int i = 0; i < numBugs; i ++){
            final BugView b = new BugView(this);
            b.setX(i * 20);
            b.setY(40);
            b.setBugType(BugView.REDBUG);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!b.getSquished()){
                        addBug(1);
                        redScore.setText(String.valueOf(Integer.valueOf(redScore.getText().toString())+1));
                    }
                    b.squished(1);
                    b.setElevation(0f);
                }
            });
            b.setElevation(1f);

            mFrameLayout.addView(b);
        }

    }

    private void addBug(final int i){
        final BugView b = new BugView(this);
        rand = new Random(50);
        b.setX(3*rand.nextInt()+1);
        b.setY(2*rand.nextInt()+1);
        b.setBugType(i);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b.getSquished()){
                    addBug(i);
                    if (i==0) {
                        blueScore.setText(String.valueOf(Integer.valueOf(blueScore.getText().toString())+1));
                    }
                    else{
                        redScore.setText(String.valueOf(Integer.valueOf(redScore.getText().toString())+1));
                    }
                }
                b.squished(i);
                b.setElevation(0f);


            }
        });
        b.setElevation(1f);

        mFrameLayout.addView(b);

    }

}

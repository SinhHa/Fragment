package com.hasbrain.howfastareyou;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import  android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.content.res.Configuration;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Date;



import java.util.ArrayList;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TapCountActivity extends AppCompatActivity {
    int tapcount =0;
    int rotate =0;
    long timepasses =0;
    ArrayList<String> index= new  ArrayList<String>();
    ArrayList<String> time= new  ArrayList<String>();





    public static final int TIME_COUNT = 10000; //10s
    @Bind(R.id.bt_tap)
    Button btTap;
    @Bind(R.id.bt_start)
    Button btStart;
    @Bind(R.id.tv_time)
    Chronometer tvTime;
    TapCountResultFragment newFragment = new TapCountResultFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ButterKnife.bind(this);




        tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - startTime >= TIME_COUNT) {
                    pauseTapping();
                }

            }
        });
    }
   @Override
    public void onConfigurationChanged(Configuration newConfig) {
       super.onConfigurationChanged(newConfig);
    rotate =1;
       if(timepasses >0 && timepasses <10000){
           timepasses = timepasses +SystemClock.elapsedRealtime()-startTime;
       }else{
       timepasses = SystemClock.elapsedRealtime()-startTime;}
        super.onConfigurationChanged(newConfig);
       tvTime.setBase(SystemClock.elapsedRealtime() + 3000);
       Toast.makeText(TapCountActivity.this,SystemClock.elapsedRealtime()-startTime  +"", Toast.LENGTH_LONG).show();
       setContentView(R.layout.activity_count);
       ButterKnife.bind(this);

       btStart.performClick();
       addFrag();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent showSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(showSettingsActivity);
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick(R.id.bt_start)
    public void onStartBtnClicked(View v) {
        startTapping();

    }

    @OnClick(R.id.bt_tap)
    public void onTapBtnClicked(View v) {
        addFrag();

    }

    private void startTapping() {
        startTime = SystemClock.elapsedRealtime();
        tvTime.setBase(SystemClock.elapsedRealtime());
        tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - startTime >= TIME_COUNT) {
                    pauseTapping();

                }

            }
        });
        if(rotate ==1){
        tvTime.setBase(SystemClock.elapsedRealtime()- timepasses);

            tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (SystemClock.elapsedRealtime() - startTime +timepasses >= TIME_COUNT) {
                        pauseTapping();
                    }

                }
            });

        }

        tvTime.start();
        btTap.setEnabled(true);
        btStart.setEnabled(false);
    }





    private void addFrag(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH/mm/ss");
        String currentDateandTime = sdf.format(new Date());

        Bundle bundle = new Bundle();


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        index.add((tapcount+1)+"");
        time.add(currentDateandTime+"");
        bundle.putStringArrayList("index",index);
        bundle.putStringArrayList("time",time);
        bundle.putInt("tap",tapcount);
        if (newFragment.getArguments() == null) {
            newFragment.setArguments(bundle );
        } else {

            newFragment.getArguments().putAll(bundle);
            transaction.detach(newFragment);
            transaction.attach(newFragment);
        }

        transaction.replace(R.id.fl_result_fragment , newFragment);
        transaction.commit();
        tapcount++;

    }


    private void pauseTapping() {
        btTap.setEnabled(false);
        tvTime.stop();
        timepasses=0;
        btTap.setEnabled(false);
        btStart.setEnabled(true);
    }



}

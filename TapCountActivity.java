package com.hasbrain.howfastareyou;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.content.res.Configuration;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.content.Context;
import java.util.Date;
import java.io.IOException;
import android.util.Log;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.io.File;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.os.Environment;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
public class TapCountActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL =1 ;
    private int tapcount = 0;
    private int rotate = 0;
    long timepasses = 0;
    int settime=10;
    static final String resulttime = "time";
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> score = new ArrayList<>();
    Bundle bundle = new Bundle();
    AppCompatTextView highscore ;

    public static final int TIME_COUNT = 10000; //10s
    @Bind(R.id.bt_tap)
    Button btTap;
    @Bind(R.id.bt_start)
    Button btStart;
    @Bind(R.id.tv_time)
    Chronometer tvTime;
    TapCountResultFragment newFragment = new TapCountResultFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    SQLdatabase  sql = new SQLdatabase(TapCountActivity.this);

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ButterKnife.bind(this);
        highscore = (AppCompatTextView) findViewById(R.id.taprecord);
        if (savedInstanceState != null) {

            for (int i = 0; i < savedInstanceState.getStringArrayList(resulttime).size(); i++) {
                time.add(i, savedInstanceState.getStringArrayList(resulttime).get(i));
                tapcount = i;
            }
            timepasses = savedInstanceState.getLong("basetime");
            tvTime.setBase(SystemClock.elapsedRealtime() - timepasses);
            rotate = 1;
        }


        tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - startTime >= TIME_COUNT) {
                    pauseTapping();
                }

            }
        });

        Cursor cr = sql.getScore(sql);
        if (cr != null)
            if (cr.moveToFirst()) {

                while (cr.isAfterLast() == false) {
                    time.add(cr.getString(0));
                    score.add(cr.getString(1));
                    cr.moveToNext();
                }
            }
        cr.close();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        SharedPreferences sharedPref = getSharedPreferences("Setting",0);
        settime= sharedPref.getInt("progress",10);

        startTime = SystemClock.elapsedRealtime();
        tvTime.setBase(SystemClock.elapsedRealtime());
        tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - startTime >= settime*1000) {
                    pauseTapping();

                }

            }
        });
        if (rotate == 1) {
            tvTime.setBase(SystemClock.elapsedRealtime() - timepasses);

            tvTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (SystemClock.elapsedRealtime() - startTime + timepasses >=settime*1000) {
                        pauseTapping();
                    }

                }
            });


        }

        tvTime.start();
        btTap.setEnabled(true);
        btStart.setEnabled(false);
    }


    private void addFrag() {
        highscore = (AppCompatTextView) findViewById(R.id.taprecord);
        tapcount++;
        highscore.setText(tapcount+"");




    }


    private void pauseTapping() {
        btTap.setEnabled(false);
        tvTime.stop();
        timepasses = 0;
        btTap.setEnabled(false);
        btStart.setEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences("Setting",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        highscore = (AppCompatTextView) findViewById(R.id.taprecord);

        if(sharedPref.getBoolean("save",false) == false){

            deletehighscore(TapCountActivity.this);
            editor.putInt("progress",10);
            editor.putInt("timelimitset",10);
            sql.clearscore(sql);

        }

        else{
            Cursor cr = sql.getScore(sql);
            cr.moveToLast();
            if(cr.getCount()< time.size()){
                for(int i=0;i<time.size();i++){
                sql.storescore(sql,time.get(i),score.get(i));
                    highscore.setText(time.size()+"");}
            }
        }




        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH/mm/ss");
        String currentDateandTime = sdf.format(new Date());
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        score.add(tapcount+"");
        time.add(currentDateandTime + "");
        bundle.putStringArrayList(resulttime, time);
        bundle.putStringArrayList("score", score);
        if (newFragment.getArguments() == null) {
            newFragment.setArguments(bundle);
        } else {

            newFragment.getArguments().putAll(bundle);
            transaction.detach(newFragment);
            transaction.attach(newFragment);
        }

        transaction.replace(R.id.fl_result_fragment, newFragment);
        transaction.commit();
        tapcount=0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(resulttime, time);
        outState.putLong("basetime",SystemClock.elapsedRealtime()-tvTime.getBase());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                pauseTapping();

                SharedPreferences sharedPref = getSharedPreferences("Setting",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("timelimitset",settime);
                editor.commit();
                Intent intent = new Intent(TapCountActivity.this,Settingactivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;


        }
        return false;
    }
    public void savedata(ArrayList<String> time) throws IOException {

        if (ContextCompat.checkSelfPermission(TapCountActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

        {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        FileOutputStream fileout = openFileOutput("Highscore.txt",Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);

        OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
        for(int i=0;i<time.size();i++){
            outputWriter.write(time.get(i).toString());
            outputWriter.write(",");

        }

        outputWriter.close();
        //display file saved message
      gethighscore();


    }

    public ArrayList<String> gethighscore() {
        ArrayList<String> temp = new ArrayList<>();
        try {
            FileInputStream fileIn = openFileInput("Highscore.txt");

            InputStreamReader isr = new InputStreamReader(fileIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

            }
            // Toast.makeText(getBaseContext(), sb.toString(),
            //       Toast.LENGTH_SHORT).show();
            String[] myArray = sb.toString().split(",");

            for (int i = 0; i < myArray.length; i++) {
                temp.add(myArray[i]);
            }
            ;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;

    }
    public void deletehighscore(Context context){
        context.deleteFile("HighScore.txt");
    }





}

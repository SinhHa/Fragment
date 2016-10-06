package com.hasbrain.howfastareyou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.SharedPreferences;

/**
 * Created by sinhhx on 10/4/16.
 */
public class Settingactivity   extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        final TextView limittime = (TextView)findViewById(R.id.Textview2);
        final SeekBar seekbar1 = (SeekBar) findViewById(R.id.SeekerBar);
        SharedPreferences sharedPref = getSharedPreferences("Setting",0);

        seekbar1.setProgress( sharedPref.getInt("progress",10));
        limittime.setText(sharedPref.getInt("progress",10)+"");
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i=i+5;
               progress =i;
                limittime.setText(progress+"");
                SharedPreferences sharedPref = getSharedPreferences("Setting",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("progress",seekbar1.getProgress()+5);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SwitchCompat saveresult =(SwitchCompat) findViewById(R.id.bt_saveresult);
        saveresult.setChecked(sharedPref.getBoolean("save",false));
        saveresult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences sharedPref = getSharedPreferences("Setting",0);
            SharedPreferences.Editor editor = sharedPref.edit();
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){

                    editor.putBoolean("save",true);
                    editor.commit();
                }else{
                    editor.putBoolean("save",false);
                    editor.commit();
                }

            }
        });


    }



}
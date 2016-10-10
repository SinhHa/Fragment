package com.hasbrain.howfastareyou;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by sinhhx on 9/26/16.
 */
public class ListResultadapter extends ArrayAdapter<String> {
    private final Context context;
    private final  ArrayList<String> timer;
    private final ArrayList<Integer> score;

    public ListResultadapter(Context context, ArrayList<String> timer, ArrayList<Integer> score) {
        super(context, R.layout.listitemresult,timer);
        this.context = context;
        this.timer = timer;
        this.score =score;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listitemresult, parent, false);
       TextView textView = (TextView) rowView.findViewById(R.id.resultindex);
        textView.setText(score.get(position).toString());
        TextView timeview = (TextView) rowView.findViewById(R.id.resulttime);
        timeview.setText(timer.get(position).toString());


        return rowView;
    }
}

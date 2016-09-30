package com.hasbrain.howfastareyou;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.app.Dialog;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/14/15.
 */
public class TapCountResultFragment extends ListFragment
{




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
    if(savedInstanceState ==null){
        Bundle bundle = this.getArguments();
        ListResultadapter adapter = new ListResultadapter(getActivity(), bundle.getStringArrayList("index") , bundle.getStringArrayList("time"));
        setListAdapter(adapter);

    }else{
        Toast.makeText(getActivity(), "destroy here", Toast.LENGTH_LONG).show();
    }return super.onCreateView(inflater, container, savedInstanceState);}

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

}


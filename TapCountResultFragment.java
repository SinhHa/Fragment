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
public class TapCountResultFragment extends ListFragment {
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        bundle = getArguments();
            ListResultadapter adapter = new ListResultadapter(getActivity(),bundle.getStringArrayList("time"),bundle.getIntegerArrayList("score"));
            setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }



}


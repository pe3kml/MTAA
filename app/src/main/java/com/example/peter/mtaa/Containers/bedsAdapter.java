package com.example.peter.mtaa.Containers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peter.mtaa.R;

import java.util.ArrayList;

/**
 * Created by Peter on 23/Feb/17.
 */

public class bedsAdapter extends ArrayAdapter {

    public bedsAdapter(Context context, int resource, ArrayList<Integer> items) {
        super(context, resource, items);
    }

    /*
    *
    * Adapter for adding list of beds into the gui
    *
    * */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.beds, null);
        }

        int p = (int) getItem(position);


        TextView tt1 = (TextView) v.findViewById(R.id.itemBed);
        tt1.setText(Integer.toString(p) + " lôžkova");

        return v;
    }


}

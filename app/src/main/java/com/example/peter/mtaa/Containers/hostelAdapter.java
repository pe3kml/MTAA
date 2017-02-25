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


public class hostelAdapter extends ArrayAdapter{


    public hostelAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
    }

     /*
    *
    * Adapter for adding enums of hostels into the gui
    *
    * */


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.hostels, null);
        }

        String p = (String) getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.itemHostel);

            tt1.setText(p);
        }

        return v;
    }


}

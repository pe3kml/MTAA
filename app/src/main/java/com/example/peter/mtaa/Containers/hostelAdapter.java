package com.example.peter.mtaa.Containers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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




    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
/*
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter;
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, hostelEnum.getAllNames());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }*/



}

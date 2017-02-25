package com.example.peter.mtaa.Containers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peter.mtaa.Data.Room;
import com.example.peter.mtaa.R;

import java.util.List;

/**
 * Created by Peter on 23/Feb/17.
 */



    public class RoomAdapter extends ArrayAdapter<Room> {


        public RoomAdapter(Context context, int resource, List<Room> items) {
            super(context, resource, items);
        }

         /*
          *
          * Adapter for adding rooms into the gui
          *
          * */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.listroomraw, null);
            }

            Room p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.id);
                TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
                TextView tt3 = (TextView) v.findViewById(R.id.description);

                if (tt1 != null) {
                    tt1.setText(p.getUsername());
                }

               if (tt2 != null) {
                    tt2.setText(Integer.toString(p.getBeds()) + " lôžkova");
                }

                if (tt3 != null) {
                    tt3.setText(Double.toString(p.getPrice()) + " eur");
                }

                if(p.isActual())
                {
                    tt1.setBackgroundColor(0xA9EB1D);
                }
            }

            return v;
        }

}

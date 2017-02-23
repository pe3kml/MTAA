package com.example.peter.mtaa.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.peter.mtaa.API.REST;
import com.example.peter.mtaa.Containers.RoomAdapter;
import com.example.peter.mtaa.Containers.bedsAdapter;
import com.example.peter.mtaa.Containers.hostelAdapter;
import com.example.peter.mtaa.Data.Room;
import com.example.peter.mtaa.Data.hostelEnum;
import com.example.peter.mtaa.R;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import static com.example.peter.mtaa.R.id.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public REST api;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.e("Created Main","123");



        api = new REST(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //menu.clear();
        //return super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        RequestParams params = new RequestParams();

        if (id == R.id.nav_room) {
            Log.e("Clicked","Parse");
            //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
           api.restinit("Rooms", params, "");

        } else if (id == R.id.nav_hostel) {
            writeHostels();

        } else if (id == R.id.nav_Type) {
            writeBeds();
        } else if (id == R.id.nav_Equipment) {

        } else if (id == R.id.nav_offline_mode) {

        } else if (id == R.id.nav_settings) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void writeBeds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Number of beds");
        ArrayList<Integer> zoznam = new ArrayList<Integer>();
        for(int i = 0; i < 7; i++)
            zoznam.add(i);
        listview = (ListView)findViewById(List);
        bedsAdapter hostel_adapter = new bedsAdapter(this, R.layout.beds, zoznam);
        //customAdapter.notifyAll();
        listview.setAdapter(hostel_adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Object selected = adapter.getItemAtPosition(position);

                //Log.d("Selected Hostel",Integer.toString(position));
                RequestParams params = new RequestParams();
                //params.put("hostel", sendback.getValue());
                api.restinit("Rooms", params, "?where=beds%3D"+selected.toString());
            }
        });

    }



    public void writeHostels() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Student hostels");
        ArrayList<String> zoznam = hostelEnum.getAllNames();
        Log.d("Adapter created","Hostels");
        listview = (ListView)findViewById(List);
        hostelAdapter hostel_adapter = new hostelAdapter(this, R.layout.hostels, zoznam);
        //customAdapter.notifyAll();
        listview.setAdapter(hostel_adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                String selected = (String) adapter.getItemAtPosition(position);
                //Log.d("Selected Hostel",Integer.toString(position));
                hostelEnum sendback = hostelEnum.getByName(selected);
                RequestParams params = new RequestParams();
                //params.put("hostel", sendback.getValue());
                api.restinit("Rooms", params, "?where=hostel%3D"+Integer.toString(sendback.getValue()));
            }
        });

    }

    public void writeListRoom(ArrayList<Room> listRoom) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        listview = (ListView)findViewById(List);
        RoomAdapter room_adapter = new RoomAdapter(this, R.layout.listroomraw, listRoom);
        //customAdapter.notifyAll();
        listview.setAdapter(room_adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                Room selected = (Room) adapter.getItemAtPosition(position);
                Log.d("Cena", Double.toString(selected.getPrice()));

            }
        });
    }


}

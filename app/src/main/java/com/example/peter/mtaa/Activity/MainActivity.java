package com.example.peter.mtaa.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.mtaa.API.ConnectivityChangeReceiver;
import com.example.peter.mtaa.API.DatabaseHelper;
import com.example.peter.mtaa.API.REST;
import com.example.peter.mtaa.Containers.RoomAdapter;
import com.example.peter.mtaa.Containers.bedsAdapter;
import com.example.peter.mtaa.Containers.hostelAdapter;
import com.example.peter.mtaa.Data.Room;
import com.example.peter.mtaa.Data.hostelEnum;
import com.example.peter.mtaa.R;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.peter.mtaa.R.id.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public REST api;
    ListView listview;
    Room selected;
    public DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(
                new ConnectivityChangeReceiver(this),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));


        myDb = new DatabaseHelper(this, this);

       // myDb.onUpgrade(myDb.getWritableDatabase(), 0, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout a = (LinearLayout) findViewById(R.id.detail1);
        a.setVisibility(View.INVISIBLE);
       // LinearLayout b = (LinearLayout) findViewById(R.id.detail1);
       // a.setVisibility(View.INVISIBLE);

        LinearLayout c = (LinearLayout) findViewById(R.id.nova);
        c.setVisibility(View.INVISIBLE);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.e("Created Main","123");

        ImageButton btn = (ImageButton) findViewById(R.id.editButton);
        btn.setVisibility(View.GONE);
        btn = (ImageButton) findViewById(R.id.saveButton);
        btn.setVisibility(View.GONE);

        api = new REST(this);

        Button reserve = (Button)findViewById(R.id.reserve);
        reserve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected.setActual(true);
                try {
                    api.put(selected);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.plusButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ImageButton editButton = (ImageButton)findViewById(R.id.editButton);
                editButton.setVisibility(View.GONE);
                ImageButton saveButton = (ImageButton)findViewById(R.id.saveButton);
                saveButton.setVisibility(View.GONE);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Add new room");

                LinearLayout f = (LinearLayout) findViewById(R.id.detail1);
                f.setVisibility(View.GONE);

                LinearLayout a = (LinearLayout) findViewById(R.id.nova);
                a.setVisibility(View.VISIBLE);
                ImageButton btn = (ImageButton) findViewById(R.id.saveButton);
                btn.setVisibility(View.VISIBLE);

                ListView b = (ListView) findViewById(R.id.List);
                b.setVisibility(View.GONE);
                btn.setSaveEnabled(true);

                final GestureDetector gestureDetector2 = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {

                        int i=0;
                        Room selected = new Room();

                        TextView a = (TextView)findViewById(R.id.addhostel);
                        selected.setHostel( a.getText().toString());
                        a = (TextView)findViewById(R.id.addprice);

                        try{
                            selected.setPrice(Double.parseDouble(a.getText().toString()));
                        }
                        catch(NumberFormatException excep){
                            i++;
                        }

                        a = (TextView)findViewById(R.id.addbeds);

                        try{
                            selected.setBeds(Integer.parseInt(a.getText().toString()));

                        }
                        catch(NumberFormatException excep){
                            i++;
                        }


                        a = (TextView)findViewById(R.id.addreconstructed);
                        if(a.getText().toString().toLowerCase().equals("yes"))
                            selected.setReconstructed(true);
                        else selected.setReconstructed(false);

                        a = (TextView)findViewById(R.id.addowner);
                        selected.setUsername(a.getText().toString());

                        a = (TextView)findViewById(R.id.addinternet);
                        if(a.getText().toString().toLowerCase().equals("yes"))
                            selected.setInternet(true);
                        else selected.setInternet(false);

                        a = (TextView)findViewById(R.id.addinfo);
                        selected.setInfo(a.getText().toString());


                        try {
                            if(i==0) api.post(selected);
                            else  Toast.makeText(MainActivity.this, "Bad number format", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        return true;
                    }

                });

                btn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        return gestureDetector2.onTouchEvent(event);
                    }
                });
                btn.setSaveEnabled(false);

            }
        });


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        api.restinit("Rooms", null, "", false);
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

    /*
     *  Choosing activity from menu
     *
     */

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
           api.restinit("Rooms", params, "", false);

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

    /*
        Showing enums in submenu (numbers of beds)
        Selecting rooms according to beds
     */

    public void writeBeds() {

        changeVisibility();

        listview.setVisibility(View.VISIBLE);
        LinearLayout a = (LinearLayout) findViewById(R.id.detail1);
        a.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Number of beds");
        ArrayList<Integer> zoznam = new ArrayList<Integer>();
        for(int i = 1; i < 7; i++)
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
                api.restinit("Rooms", params, "?where=beds%3D"+selected.toString(), false);

            }
        });

    }

      /*
        Showing enums in submenu (hostels)
        Selecting rooms according to hostels
     */
    public void writeHostels() {
        changeVisibility();

        listview.setVisibility(View.VISIBLE);
        LinearLayout a = (LinearLayout) findViewById(R.id.detail1);
        a.setVisibility(View.GONE);

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
                api.restinit("Rooms", params, "?where=hostel%3D"+Integer.toString(sendback.getValue()), false);

            }
        });

    }

    /*
    *
    *   Showing list of rooms and action listener for showing details on room
    *   Action listener for alert to delete
    * */

    public void writeListRoom(ArrayList<Room> listRoom) {
        changeVisibility();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        listview = (ListView)findViewById(List);

        listview.setVisibility(View.VISIBLE);
        LinearLayout a = (LinearLayout) findViewById(R.id.detail1);
        a.setVisibility(View.GONE);

        RoomAdapter room_adapter = new RoomAdapter(this, R.layout.listroomraw, listRoom);
        //customAdapter.notifyAll();

        listview.setAdapter(room_adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                selected = (Room) adapter.getItemAtPosition(position);
                Log.d("Cena", Double.toString(selected.getPrice()));
                showDetails(selected);
                LinearLayout a = (LinearLayout) findViewById(R.id.detail1);
                a.setVisibility(View.VISIBLE);
                ListView b = (ListView) findViewById(R.id.List);
                b.setVisibility(View.GONE);

            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
                //Log.v("long clicked","pos: " + pos);
                //showDialog(MainActivity, "a", );
                alert(selected = (Room) adapter.getItemAtPosition(position));
                return true;
            }
        });


    }

    /*
    *
    *  Action listener for deleting room
    *
    * */
    public void alert(final Room room)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete item...");
        dialog.setMessage("Do you want to delete this item?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.setPositiveButton("Yes, I do", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                api.delete(room);
            }
        });
        dialog.show();
    }

    /*
    *
    *   Showing details of room in gui
    *   Action listener for editing details
    *   Action listener for saving details
    * */

    public void showDetails(final Room selected)
    {
        LinearLayout c = (LinearLayout) findViewById(R.id.nova);
        c.setVisibility(View.INVISIBLE);

        Button reserve = (Button)findViewById(R.id.reserve);
        if(selected.isActual()) reserve.setClickable(false);
        else reserve.setClickable(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        EditText set = (EditText)findViewById(R.id.hostel);
        set.setText(selected.getHostel());
        set = (EditText)findViewById(R.id.price);
        set.setText(Double.toString(selected.getPrice()));
        set = (EditText)findViewById(R.id.beds);
        set.setText(Integer.toString(selected.getBeds()));
        set = (EditText)findViewById(R.id.reconstructed);
        if(selected.isReconstructed()) set.setText("Reconstructed");
        set = (EditText)findViewById(R.id.username);
        set.setText(selected.getUsername());
        set = (EditText)findViewById(R.id.internet);
        if(selected.isInternet()) set.setText("Yes");
        set = (EditText)findViewById(R.id.info);
        set.setText(selected.getInfo());

        ImageButton btn = (ImageButton) findViewById(R.id.editButton);
        ImageButton btn2 = (ImageButton) findViewById(R.id.saveButton);

        showImage(selected.getImage());

        btn.setVisibility(View.VISIBLE);
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                EditText set = (EditText)findViewById(R.id.hostel);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.price);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.beds);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.reconstructed);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.username);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.internet);
                set.setEnabled(true);
                set.setClickable(true);
                set = (EditText)findViewById(R.id.info);
                set.setEnabled(true);
                set.setClickable(true);

                return true;
            }

        });

       btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gestureDetector.onTouchEvent(event);
            }
        });



        btn2.setVisibility(View.VISIBLE);

        final GestureDetector gestureDetector2 = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                int i = 0;
                TextView a = (TextView)findViewById(R.id.hostel);
                selected.setHostel( a.getText().toString());
                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.price);
                try{
                    selected.setPrice(Double.parseDouble(a.getText().toString()));
                }
                catch(NumberFormatException excep){
                    i++;
                }


                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.beds);
                try{
                    selected.setBeds(Integer.parseInt(a.getText().toString()));
                }
                catch(NumberFormatException excep){
                    i++;
                }

                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.reconstructed);
                if(a.getText().toString().toLowerCase().equals("yes"))
                    selected.setReconstructed(true);
                else selected.setReconstructed(false);
                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.username);
                selected.setUsername(a.getText().toString());
                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.internet);
                if(a.getText().toString().toLowerCase().equals("yes"))
                    selected.setInternet(true);
                else selected.setInternet(false);
                a.setEnabled(false);
                a.setClickable(false);
                a = (TextView)findViewById(R.id.info);
                selected.setInfo(a.getText().toString());
                a.setEnabled(false);
                a.setClickable(false);

                try {
                    if(i==0) api.put(selected);
                    else Toast.makeText(MainActivity.this, "Bad number format", Toast.LENGTH_SHORT).show();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                return true;
            }

        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gestureDetector2.onTouchEvent(event);
            }
        });
        btn2.setSaveEnabled(false);

    }



    /*
    *   Alert for showing message
    *
    * */
    public void alertSuccess(String string)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(string);
        dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
       dialog.show();
    }

    /*
    *   Change visibilty of edit and save button
    *
    * */

    public void changeVisibility()
    {
        ImageButton editButton = (ImageButton)findViewById(R.id.editButton);
        editButton.setVisibility(View.GONE);
        ImageButton saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setVisibility(View.GONE);
        LinearLayout a = (LinearLayout) findViewById(R.id.nova);
        a.setVisibility(View.GONE);

    }


    public void showImage(String fileUrl){
        try {

            Bitmap bitmap = image(fileUrl);
            ImageView img = (ImageView)findViewById(R.id.imageDetail);
            img.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static Bitmap image(String url) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ERROR");
        return null;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void callUpgrade()
    {
        ArrayList<Room> listRoom = myDb.getAllData();
        for(int i = 0; i < listRoom.size(); i++)
        {
            try {
                if(listRoom.get(i).getAction()==1)
                    api.post(listRoom.get(i));
                if(listRoom.get(i).getAction()==2)api.put(listRoom.get(i));
                if(listRoom.get(i).getAction()==3)api.delete(listRoom.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("Console","May inserting to SQLite");
        myDb.onUpgrade(myDb.getWritableDatabase(),0,0);
        api.restinit("Rooms", null, "", true);


    }

    public void addToSql(ArrayList<Room> listRoom) {

        Log.e("Console","Getting on data from table");

        for(int i = 0; i < listRoom.size(); i++)
        {
            Room room = listRoom.get(i);
            room.setAction(0);
            myDb.insertData(room);
        }


    }
}







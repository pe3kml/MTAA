package com.example.peter.mtaa.API;

import android.util.Log;

import com.example.peter.mtaa.Activity.MainActivity;
import com.example.peter.mtaa.Data.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Peter on 23/Feb/17.
 */

public class parseJSON {

    MainActivity ref_activity;

    public parseJSON(MainActivity ref_activity)
    {
        this.ref_activity = ref_activity;
    }

    public void parse(String response, String what)
    {
        JSONObject myObject = null;
        try {
            Log.d("Retazec : ",response);
            myObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }



        switch(what)
        {
            case "Rooms": parseRoom(myObject);

        }



    }

    public void parseRoom(JSONObject myObject)  {
        ArrayList<Room> listRoom = new ArrayList<>();
        JSONArray myArray = null;
        try {
            myArray = myObject.getJSONArray("data");
            for(int i = 0; i < myArray.length(); i++)
            {
                JSONObject finall = myArray.getJSONObject(i);
                Room room = new Room();
                room.setActual(finall.getBoolean("actual"));
                room.setRoom_id(finall.getInt("room_id"));
                room.setUsername(finall.getString("username"));
                room.setReconstructed(finall.getBoolean("reconstructed"));
                room.setPrice(finall.getDouble("price"));
                room.setInternet(finall.getBoolean("internet"));
                room.setInfo(finall.getString("info"));
                room.setImage(null);
                room.setDate(null);
                room.setBeds(finall.getInt("beds"));

                listRoom.add(room);
            }

            ref_activity.writeListRoom(listRoom);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




}

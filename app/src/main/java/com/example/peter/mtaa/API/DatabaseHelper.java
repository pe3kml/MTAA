package com.example.peter.mtaa.API;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.peter.mtaa.Activity.MainActivity;
import com.example.peter.mtaa.Data.Room;
import com.example.peter.mtaa.Data.hostelEnum;

import java.util.ArrayList;

/**
 * Created by Peter on 15/Mar/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLite";
    public static final String TABLE_NAME = "Room";
    public static final String COL_1 = "username";
    public static final String COL_2 = "object_id";
    public static final String COL_3 = "reconstructed";
    public static final String COL_4 = "price";
    public static final String COL_5 = "internet";
    public static final String COL_6 = "info";
    public static final String COL_7 = "image";
    public static final String COL_8 = "hostel";
    public static final String COL_9 = "beds";
    public static final String COL_10 = "actual";
    public static  final String COL_11 = "action";



    public DatabaseHelper(Context context, MainActivity ref_activity) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Console","Creating off table");
        db.execSQL("create table " + TABLE_NAME + "( username TEXT, object_id text, reconstructed integer, price double, internet integer, info TEXT, image text, hostel integer, beds integer, actual integer, action integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Console","Dropping off table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, room.getUsername());
        contentValues.put(COL_2, room.getObject());
        if (room.isReconstructed()) contentValues.put(COL_3, 1);
        else contentValues.put(COL_3, 0);
        contentValues.put(COL_4, room.getPrice());
        if (room.isInternet()) contentValues.put(COL_5, 1);
        else contentValues.put(COL_5, 1);
        contentValues.put(COL_6, room.getInfo());
        contentValues.put(COL_7, room.getImage());
        contentValues.put(COL_8, hostelEnum.getInt(hostelEnum.getByName(room.getHostel())));
        contentValues.put(COL_9, room.getBeds());
        if (room.isActual()) contentValues.put(COL_10, 1);
        else contentValues.put(COL_10, 0);
        contentValues.put(COL_11, room.getAction());

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    ArrayList<Room> listRoom;

    public ArrayList<Room> getAllData() {
        Log.d("Console","Getting off data from table");
         listRoom = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                Room room = new Room();
                room.setUsername(res.getString(0));
                room.setObject(res.getString(1));
                if (res.getInt(2) == 1) room.setReconstructed(true);
                else room.setReconstructed(false);
                room.setPrice(res.getDouble(3));
                if (res.getInt(4) == 1) room.setInternet(true);
                else room.setInternet(false);
                room.setInfo(res.getString(5));
                room.setImage(res.getString(6));
                room.setHostel(hostelEnum.getByValue(res.getInt(7)).toString());
                room.setBeds(res.getInt(8));
                if (res.getInt(9) == 1) room.setActual(true); //skontroluj si tie hodnoty
                else room.setActual(false);
                room.setAction(res.getInt(10));
                listRoom.add(room);
            }
        }

        return listRoom;
    }

    public boolean update(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, room.getUsername());
        contentValues.put(COL_2, room.getObject());
        if (room.isReconstructed()) contentValues.put(COL_3, 1);
        else contentValues.put(COL_3, 0);
        contentValues.put(COL_4, room.getPrice());
        if (room.isInternet()) contentValues.put(COL_5, 1);
        else contentValues.put(COL_5, 1);
        contentValues.put(COL_6, room.getInfo());
        contentValues.put(COL_7, room.getImage());
        contentValues.put(COL_8, hostelEnum.getInt(hostelEnum.getByName(room.getHostel())));
        contentValues.put(COL_9, room.getBeds());
        if (room.isActual()) contentValues.put(COL_10, 1);
        else contentValues.put(COL_10, 0);
        contentValues.put(COL_11, room.getAction());

        db.update(TABLE_NAME, contentValues, "object_id = ?", new String[]{room.getObject()});
        return true;
    }

    public Integer delete(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        room.setAction(3);
        update(room);
        //return db.delete(TABLE_NAME, "object_id = ?", new String[]{room.getObject()});
        return 1;
    }


}

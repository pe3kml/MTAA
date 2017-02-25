package com.example.peter.mtaa.API;

import android.util.Log;

import com.example.peter.mtaa.Activity.MainActivity;
import com.example.peter.mtaa.Data.Room;
import com.example.peter.mtaa.Data.hostelEnum;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * Created by Peter on 22/Feb/17.
 */

public class REST
{
    MainActivity ref_activity;
    AsyncHttpClient client;

    public REST(MainActivity ref_activity)
    {
        this.ref_activity = ref_activity;
        client = new AsyncHttpClient();
        client.addHeader("application-id", "0E4865CB-A0FD-8F5F-FF5D-0F10B0B9D700");
        client.addHeader("secret-key", "23A31933-17C5-5F99-FF5F-1549AB601300");
        //client.setBasicAuth();
    }

    public void restinit(final String http, final RequestParams params, String arg)
    {


        Log.e("JSON","Parse");
        //bude treba dorobit nejaky znak ze to nacitava zo servera
        String url = new String("http://api.backendless.com/v1/data/"+http+arg);
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                parseJSON parse = new parseJSON(ref_activity);
                parse.parse(response, http);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //tu bude treba dorobit rozoznavanie jednotlivych kodov
                //Toast.makeText(applicationContext, "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void post(Room selected) throws JSONException {
        JSONObject jsonParams = new JSONObject();

        jsonParams.put("room_id", selected.getRoom_id());
        jsonParams.put("image", null);
        jsonParams.put("actual", selected.isActual());
        jsonParams.put("reconstructed", selected.isReconstructed());
        int cislo = hostelEnum.getInt(hostelEnum.getByName(selected.getHostel()));
        jsonParams.put("hostel", cislo);
        //jsonParams.put("created", selected.getc);
        //jsonParams.put("dateRealesed", selected.getRoom_id());
        jsonParams.put("ownerId", null);
        jsonParams.put("price", selected.getPrice());
        jsonParams.put("beds", selected.getBeds());
        //jsonParams.put("updated", selected.getRoom_id());
        //jsonParams.put("objectId", selected.get);
        jsonParams.put("internet", selected.isInternet());
        jsonParams.put("username", selected.getUsername());
        jsonParams.put("info", selected.getInfo());

        String str = jsonParams.toString();
        StringEntity entity = null;
        try {
            entity = new StringEntity(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("JSONIK",str);
        client.put(ref_activity.getApplicationContext(), "http://api.backendless.com/v1/data/Rooms?where=objectId%3D" + selected.getObject(), entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Put", Integer.toString(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Put",Integer.toString(statusCode));
            }
        });


       // client.post(context, restApiUrl, entity, "application/json", responseHandler);
    }


    public void delete(Room room)
    {
        client.delete("http://api.backendless.com/v1/data/Rooms/" + room.getObject(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Delete", Integer.toString(statusCode));
                ref_activity.alertSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Delete",Integer.toString(statusCode));
            }
        });
    }




}

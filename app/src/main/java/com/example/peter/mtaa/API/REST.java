package com.example.peter.mtaa.API;

import android.util.Log;

import com.example.peter.mtaa.Activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Peter on 22/Feb/17.
 */

public class REST
{
    MainActivity ref_activity;


    public REST(MainActivity ref_activity)
    {
        this.ref_activity = ref_activity;

    }

    public void restinit(final String http, final RequestParams params, String arg)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("application-id", "0E4865CB-A0FD-8F5F-FF5D-0F10B0B9D700");
        client.addHeader("secret-key", "394E3F2E-85B5-1230-FF23-E858F4550400");

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

}

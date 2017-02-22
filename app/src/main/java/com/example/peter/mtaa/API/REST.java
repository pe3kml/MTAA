package com.example.peter.mtaa.API;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Peter on 22/Feb/17.
 */

public class REST
{
    public REST()
    {

    }

    public void restinit(final Context applicationContext)
    {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        client.addHeader("application-id", "0E4865CB-A0FD-8F5F-FF5D-0F10B0B9D700");
        client.addHeader("secret-key", "394E3F2E-85B5-1230-FF23-E858F4550400");
        client.get("http://api.backendless.com/v1/data/Rooms", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(applicationContext, "Succes!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }


}

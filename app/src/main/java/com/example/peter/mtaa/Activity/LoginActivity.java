package com.example.peter.mtaa.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peter.mtaa.API.ConnectivityChangeReceiver;
import com.example.peter.mtaa.R;

public class LoginActivity extends AppCompatActivity {

    EditText InputEmail;
    EditText InputPassword;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputEmail = (EditText)findViewById(R.id.InputEmail);
        InputPassword = (EditText)findViewById(R.id.InputPassword);
        btn = (Button)findViewById(R.id.ButtonSignIn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("asd","asd");
                Login();
                return false;
            }
        });

        registerReceiver(
                new ConnectivityChangeReceiver(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));


    }

    /*
    *
    * Static login
    *
    * */

    protected void Login()
    {
        String localEmail = "p3@a.sk";
        String localPassword = "3";
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if(email.equals(localEmail) && password.equals(localPassword) && isValidEmail(email))
        {
            navigateToHomeActivity();
        }else
        {
            Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
        }


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /*
    * Start main acitivity
    *
    * */

    public void navigateToHomeActivity()
    {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }



}

package com.example.peter.mtaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText InputEmail;
    EditText InputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputEmail = (EditText)findViewById(R.id.InputEmail);
        InputPassword = (EditText)findViewById(R.id.InputPassword);
    }


    protected void Login()
    {
        String localEmail = "pe3k778899@azet.sk";
        String localPassword = "123456";
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if(email.equals(localEmail) && password.equals(localPassword))
        {
            navigateToHomeActivity();
        }else
        {
            //error stav
        }


    }

    public void navigateToHomeActivity()
    {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }




}

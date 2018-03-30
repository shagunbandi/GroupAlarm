package com.justapp.groupalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername = (EditText) findViewById(R.id.UPTName);
        final EditText etEmail = (EditText) findViewById(R.id.UPTEmail);
        final TextView message = (TextView) findViewById(R.id.userHeading);

    }
}

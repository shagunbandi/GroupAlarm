package com.justapp.groupalarm;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button Lbutton;
    TextView message;
    TextView messageNoLogin;
    String URL_POST = "http://ff2da018.ngrok.io/api/accounts/login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail  = (EditText) findViewById(R.id.LEmail);
        etPassword = (EditText) findViewById(R.id.LPassword);
        Lbutton = (Button) findViewById(R.id.LButton);
        message = (TextView) findViewById(R.id.LMessage);
        messageNoLogin = (TextView) findViewById(R.id.LMessageNoLogin);

        message.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent resgisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                        LoginActivity.this.startActivity(resgisterIntent);
                    }
                }
        );

        messageNoLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent noLoginIntent = new Intent(LoginActivity.this, ListActivity.class);
                        LoginActivity.this.startActivity(noLoginIntent);
                    }
                }
        );

        Lbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login_helper();
                    }
                }
        );


    }

    private void login_helper() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_POST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String user_ret = json.getString("email");

                            boolean success = user_ret.equals(etEmail.getText().toString());
                            if (success){
                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                LoginActivity.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error+"", Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", etEmail.getText().toString());
                params.put("password", etPassword.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.start();

    }
}

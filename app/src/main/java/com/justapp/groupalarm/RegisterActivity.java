package com.justapp.groupalarm;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmail;
    EditText etPassword;
    Button Rbutton;
    String message;
    String URL_POST = "http://0003fcaf.ngrok.io/api/accounts/register/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        message = "Follwing \n";
        etName = (EditText) findViewById(R.id.RName);
        etEmail = (EditText) findViewById(R.id.REmail);
        etPassword = (EditText) findViewById(R.id.RPassword);
        Rbutton = (Button) findViewById(R.id.RButton);

        Rbutton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register_helper();
                }
            }
        );

    }

    private void register_helper() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_POST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String user_ret = json.getString("username");

                            boolean success = user_ret.equals(etName.getText().toString());
                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Failed")
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
                            Toast.makeText(RegisterActivity.this, error+"", Toast.LENGTH_LONG).show();
                        }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", etName.getText().toString());
                params.put("email", etEmail.getText().toString());
                params.put("email2", etEmail.getText().toString());
                params.put("password", etPassword.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.start();

    }

}
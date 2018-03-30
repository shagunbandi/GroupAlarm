package com.justapp.groupalarm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity {

    final String TAG = "SHAGUN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String[] foods = {"Pizza", "Paratha", "Cheese"};
        ListAdapter myAdapter = new CustomAdapter(this, foods);
        ListView myList = (ListView) findViewById(R.id.myList);
        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(ListActivity.this, food, Toast.LENGTH_LONG).show();
//                        TODO Switch to Main Alarm
                    }
                }
        );
    }


}

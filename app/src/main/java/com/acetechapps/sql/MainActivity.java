package com.acetechapps.sql;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText writtenQuery = (EditText) findViewById(R.id.inPut);
    String getQuery;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void save(View clicked) {

        Toast.makeText(getApplicationContext(), "Query not Saved", Toast.LENGTH_SHORT).show();
    }

    public void clear(View view) {

        getQuery = "Start Typing Query...";
        writtenQuery.setText(getQuery, TextView.BufferType.EDITABLE);
        Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_SHORT).show();
    }

    public void runQuery(View clicked) {

        Toast.makeText(getApplicationContext(), "Will Run Eventually",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_output);
    }

}

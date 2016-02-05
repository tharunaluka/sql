package com.acetechapps.sql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String getQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void save (View view) {

    }

    public void clear (View view) {

    }

    public void run (View view) {

    }

    public void inPut (View view) {

        EditText writtenQuery = (EditText)findViewById(R.id.inPut);
        getQuery = writtenQuery.getText().toString();

    }

}

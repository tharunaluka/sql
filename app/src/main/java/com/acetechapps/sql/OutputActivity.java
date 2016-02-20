package com.acetechapps.sql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

/**
 * Created by tharunaluka on 05/02/16.
 */
public class OutputActivity extends AppCompatActivity {


    private Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_output);

            toolbar = (Toolbar) findViewById(R.id.appBar);
            setSupportActionBar(toolbar);

        }


        public void export (View view){
            Toast.makeText(getApplicationContext(), "Nothing to Export",Toast.LENGTH_SHORT).show();
        }

        public void back (View view){
            setContentView(R.layout.activity_main);
        }

}
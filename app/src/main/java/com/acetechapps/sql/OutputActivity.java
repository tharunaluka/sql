package com.acetechapps.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.sql.ResultSet;

/**
 * Created by tharunaluka on 05/02/16.
 */
public class OutputActivity extends AppCompatActivity implements View.OnClickListener {



    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        toolbar = (Toolbar) findViewById(R.id.appBarOutput);
        setSupportActionBar(toolbar);
        ImageView backView = (ImageView) findViewById(R.id.ic_back);
        backView.setOnClickListener(this);

        TableLayout tl = (TableLayout) findViewById(R.id.main_table);

        if(Utils.results != null){

         //   ResultSet resultSet = Utils.results;
         //   ResultSetMetaData rsmd = resultSet.getMetaData();
         //   int columnsNumber = rsmd.getColumnCount();
         //   while (resultSet.next()) {
         //       for (int i = 1; i <= columnsNumber; i++) {
         //           if (i > 1) System.out.print(",  ");
         //           String columnValue = resultSet.getString(i);
         //           tl.(columnValue + " " + rsmd.getColumnName(i));
         //       }
         //       System.out.println("");
         //   }

           Toast.makeText(getApplicationContext(), "is not null", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(), "is null", Toast.LENGTH_SHORT).show();
        }

    }





    public void export(View view) {
        Toast.makeText(getApplicationContext(), "Nothing to Export", Toast.LENGTH_SHORT).show();
    }

    public void goBack() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ic_back) {
            goBack();
        }
    }
}
package com.acetechapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


/**
 * Created by tharunaluka on 25/02/16.
 */


public class QueryActivity extends AppCompatActivity {

    dbHelper sqlDB;
    EditText queryName, queryText;
    Button saveQuery;
    ImageButton addQuery;
    String[]  savedQueryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        sqlDB = new dbHelper(this);

        queryName = (EditText) findViewById(R.id.editQueryName);
        queryText = (EditText) findViewById(R.id.editQueryText);
        saveQuery = (Button) findViewById(R.id.saveQuery);
        addQuery = (ImageButton) findViewById(R.id.addAQuery);

        Cursor res = sqlDB.showAllQueries();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.allSavedQueries);

        int j = 0;
        int queryRows = res.getColumnCount();
        while (res.moveToNext()) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int i = 1; i <= queryRows; i++) {
                TextView textView = new TextView(this);
                textView.setText(res.getString(j));
                row.addView(textView);
            }
            tableLayout.addView(row, j++);
        }
    }

    public void addQuery(View clicked){
        setContentView(R.layout.add_query);
    }

    public void saveQuery(View clicked){

      //  boolean inserted =  sqlDB.insertData(queryName.getText().toString(),queryText.getText().toString());
      //      if (inserted == true){
       //         Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
       //     }else{
         //       Toast.makeText(getApplicationContext(),"Data not Inserted",Toast.LENGTH_SHORT).show();
           // }
        queryName = (EditText) findViewById(R.id.editQueryName);
        queryText = (EditText) findViewById(R.id.editQueryText);
            sqlDB.insertData(queryName.getText().toString(),queryText.getText().toString());
            setContentView(R.layout.activity_queries);
        }
    }
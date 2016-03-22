package com.acetechapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tharunaluka on 25/02/16.
 */


public class QueryActivity extends AppCompatActivity {

    dbHelper sqlDB;
    EditText queryName, queryText;
    Button saveQuery;
    ImageButton addQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        queryName = (EditText) findViewById(R.id.editQueryName);
        queryText = (EditText) findViewById(R.id.editQueryText);
        saveQuery = (Button) findViewById(R.id.saveQuery);
        addQuery = (ImageButton) findViewById(R.id.addAQuery);
        showingQueries();
    }

    public void showingQueries() {

        sqlDB = new dbHelper(this);

        Cursor res = sqlDB.showAllQueries();

        int queryRows = res.getColumnCount();
        int k = 0;
        if (res != null) {
            TableLayout tb = (TableLayout) findViewById(R.id.allSavedQueries);
            while (res.moveToNext()) {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView textView = new TextView(this);
                textView.setText(res.getString(1));
                textView.setPadding(8, 8, 8, 8);
                textView.setGravity(Gravity.LEFT);
                textView.setBackgroundColor(Color.parseColor("#D4E157"));
                textView.setTypeface(null, Typeface.BOLD);
                //params.setMargins(0, 0, 0, 0);
                row.addView(textView);
                tb.addView(row, k++);

                row = new TableRow(this);
                TextView textViews = new TextView(this);
                textViews.setText(res.getString(2));
                textViews.setPadding(8, 8, 8, 8);
                textViews.setGravity(Gravity.LEFT);
                textViews.setBackgroundColor(Color.parseColor("#ffffff"));
                //params.setMargins(0, 0, 0, 16);
                row.addView(textViews);
                tb.addView(row, k++);
            }
        }else{
            Toast.makeText(getApplicationContext(), "No Queries Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void addQuery(View clicked){
        setContentView(R.layout.add_query);
    }

    public void saveQuery(View clicked){
        queryName = (EditText) findViewById(R.id.editQueryName);
        queryText = (EditText) findViewById(R.id.editQueryText);
            sqlDB.insertData(queryName.getText().toString(),queryText.getText().toString());
            setContentView(R.layout.activity_queries);
        showingQueries();
    }
}
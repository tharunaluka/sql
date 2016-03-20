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
        showingQueries();
    }

    public void showingQueries() {

        sqlDB = new dbHelper(this);
        queryName = (EditText) findViewById(R.id.editQueryName);
        queryText = (EditText) findViewById(R.id.editQueryText);
        saveQuery = (Button) findViewById(R.id.saveQuery);
        addQuery = (ImageButton) findViewById(R.id.addAQuery);

        Cursor res = sqlDB.showAllQueries();

        int queryRows = res.getColumnCount();
        int k = 0;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Utils.results != null) {
            TableLayout tb = (TableLayout) findViewById(R.id.allSavedQueries);
            while (res.moveToNext()) {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                for (int j = 1; j < queryRows; j++) {
                    TextView textView = new TextView(this);
                    textView.setText(res.getString(j));
                    textView.setPadding(8, 8, 8, 8);
                    textView.setGravity(Gravity.LEFT);
                    textView.setBackgroundColor(Color.parseColor("#D4E157"));
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setId(k);
                    params.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                    tb.addView(row, k);

                    TextView textViews = new TextView(this);
                    textViews.setText(res.getString(j));
                    textViews.setPadding(8, 8, 8, 8);
                    textViews.setGravity(Gravity.LEFT);
                    textViews.setBackgroundColor(Color.parseColor("#ffffff"));
                    textViews.setId(k + 1);
                    params.setMargins(0, 0, 0, 16);
                    textViews.setLayoutParams(params);
                    row.addView(textViews);
                    tb.addView(row, k + 1);
                }
                k = k + 2;
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
    }
}
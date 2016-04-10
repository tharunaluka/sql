package com.acetechapps.sql;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.acetechapps.sql.db.DbHelper;

import java.sql.ResultSet;

/**
 * Created by tharunaluka on 25/02/16.
 */


public class QueryActivity extends AppCompatActivity {

    DbHelper sqlDB;
    EditText queryName, queryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        showingQueries();
    }

    public void setViewAndDisplayResult(ResultSet rs) throws Exception{
        Utils.results = rs;
        Intent i = new Intent(this, OutputActivity.class);
        startActivity(i);
    }

    public void showingQueries() {

        sqlDB = new DbHelper(this);

        Cursor res = sqlDB.showAllQueries();
        int k = 0;
        if (res != null) {
            TableLayout tb = (TableLayout) findViewById(R.id.allSavedQueries);
            while (res.moveToNext()) {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
                row.setLayoutParams(params);
                TextView textView = new TextView(this);
                textView.setText(res.getString(1));
                textView.setPadding(8, 8, 8, 8);
                textView.setGravity(Gravity.LEFT);
                textView.setBackgroundColor(Color.parseColor("#D4E157"));
                textView.setTypeface(null, Typeface.BOLD);
                row.addView(textView);
                tb.addView(row, k++);

                row = new TableRow(this);
                params.setMargins(8, 8, 8, 16);
                row.setLayoutParams(params);
                TextView textViews = new TextView(this);
                textViews.setText(res.getString(2));
                textViews.setPadding(8, 8, 8, 8);
                textViews.setGravity(Gravity.LEFT);
                textViews.setBackgroundColor(Color.parseColor("#ffffff"));
                textViews.setLayoutParams(params);
                row.addView(textViews);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String qryText = ((TextView) ((ViewGroup) v).getChildAt(0)).getText().toString();
                        if (Utils.connection == null) {
                            Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(QueryActivity.this, OutputActivity.class);
                        MainActivity.RunQueryTask task = new MainActivity().new RunQueryTask(QueryActivity.this, intent);
                        task.execute(qryText);
                    }
                });
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
package com.acetechapps.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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


        if(Utils.results != null){
            TableLayout ll = (TableLayout) findViewById(R.id.testOutput);

            ResultSet resultSet = Utils.results;
            int k=0;
            try {
                int i=resultSet.getMetaData().getColumnCount();
                ResultSetMetaData rsmd = resultSet.getMetaData();
                TableRow header= new TableRow(this);
                TableRow.LayoutParams headerLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                header.setLayoutParams(headerLayoutParams);
                TextView textView = new TextView(this);
                textView.setText("");
                textView.setBackgroundColor(Color.parseColor("#D4E157"));
                textView.setPadding(8,8,8,8);
                header.addView(textView);
                for (int col = 1; col <= i; col++){
                    textView = new TextView(this);
                    textView.setBackgroundColor(Color.parseColor("#D4E157"));
                    textView.setPadding(8,8,8,8);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setText(rsmd.getColumnName(col));
                    header.addView(textView);
                }
                ll.addView(header, k++);
                while (resultSet.next()){
                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    textView = new TextView(this);
                    textView.setText(String.valueOf(k));
                    textView.setPadding(8,8,8,8);
                    textView.setGravity(Gravity.RIGHT);
                    row.addView(textView);
                    for(int j=1;j<=i;j++){
                        textView = new TextView(this);
                        textView.setText(resultSet.getString(j));
                        row.addView(textView);
                    }
                    ll.addView(row, k++);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
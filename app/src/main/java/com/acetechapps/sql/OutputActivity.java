package com.acetechapps.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
            String result="";
            int k=0;
            try {
                int i=resultSet.getMetaData().getColumnCount();
                ResultSetMetaData rsmd = resultSet.getMetaData();
                TableRow header= new TableRow(this);
                TableRow.LayoutParams headerLayoutparams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                header.setLayoutParams(headerLayoutparams);
                for (int col = 1; col <= i; i++){
                    TextView textView = new TextView(this);
                    textView.setText(rsmd.getColumnName(col));
                    header.addView(textView);
                }
                while (resultSet.next()){
                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    for(int j=1;j<=i;j++){
                        TextView textView = new TextView(this);
                        textView.setText(resultSet.getString(j));
                        row.addView(textView);
                    }
                    ll.addView(row, k++);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //TextView vi = (TextView) findViewById(R.id.outPut);
            //vi.setText(result);
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
package com.acetechapps.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.acetechapps.sql.service.ActivityService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements ActivityService {

    EditText writtenQuery;
    String getQuery;
    SharedPreferences preferences;
    Connection connection;
    TextView textView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        if(Utils.connection!=null){
            connection = Utils.connection;
        } else {
            //start connect activity again!
            Toast.makeText(getApplicationContext(), "That's a bummer!", Toast.LENGTH_SHORT).show();
        }
        writtenQuery = (EditText) findViewById(R.id.inPut);
    }

    public void save(View clicked) {
        Toast.makeText(getApplicationContext(), "Query not Saved", Toast.LENGTH_SHORT).show();
    }

    public void clear(View view) {
        getQuery = "Start Typing Query...";
        writtenQuery.setText(getQuery, TextView.BufferType.EDITABLE);
    }

    public void runQuery(View clicked) {
        new RunQueryTask().execute(writtenQuery.getText().toString());
    }

    public void setViewAndDisplayResult(ResultSet rs) throws Exception{
        setContentView(R.layout.activity_output);
        String resultString = "Showing only the id of the result \n";
        textView = (TextView) findViewById(R.id.testOutput);
        while (rs.next()) {
            resultString += rs.getString(1) + "\n";
        }
        textView.setText(resultString);
    }

    public class RunQueryTask extends AsyncTask<String, Integer, Boolean> {

        ResultSet rs;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (connection != null) {
                    Statement stmt = connection.createStatement();
                    rs = stmt.executeQuery(params[0]);
                    System.out.println("result " + rs);

                    return true;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            if(result) {
                try {
                    setViewAndDisplayResult(rs);
                } catch (Exception e){
                    e.printStackTrace();
                }

            } else{
                Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void openDrawer(View view) {
        Intent i= new Intent(this, LeftMenuActivity.class);
        startActivity(i);
    }
}

package com.acetechapps.sql;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText writtenQuery;
    String getQuery;
    SharedPreferences preferences;
    Connection connection;
    ResultSet resultSet;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Utils.connection!=null){
            connection = Utils.connection;
        } else {
            //start connect activity again!
            Toast.makeText(getApplicationContext(), "Thats a bummer!", Toast.LENGTH_SHORT).show();
        }
        writtenQuery = (EditText) findViewById(R.id.inPut);
    }

    public void save(View clicked) {
        Toast.makeText(getApplicationContext(), "Query not Saved", Toast.LENGTH_SHORT).show();
    }

    public void clear(View view) {
        getQuery = "Start Typing Query...";
        writtenQuery.setText(getQuery, TextView.BufferType.EDITABLE);
        Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_SHORT).show();
    }

    public void runQuery(View clicked) {
        new RunQueryTask().execute(writtenQuery.getText().toString());
        Toast.makeText(getApplicationContext(), "Will Run Eventually",Toast.LENGTH_SHORT).show();
    }

    public void setViewAndDisplayResult(ResultSet rs) throws Exception{
        setContentView(R.layout.activity_output);
        String resultString = "";
        textView = (TextView) findViewById(R.id.testOutput);
        while (rs.next()) {
            resultString += rs.getString("id");
            System.out.println(rs.getString("id") + "\n");
        }
        textView.setText(resultString);
    }

    public class RunQueryTask extends AsyncTask<String, Integer, Boolean> {

        String resultString = "oh no!";
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
                Toast.makeText(getApplicationContext(), "Cannot create connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

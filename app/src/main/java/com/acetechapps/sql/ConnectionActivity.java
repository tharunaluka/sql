package com.acetechapps.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionActivity extends AppCompatActivity{

    EditText host;
    EditText port;
    EditText username;
    EditText password;
    EditText name;
    EditText dbName;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        preferences = getSharedPreferences(Utils.PREFERENCES, 0);
        boolean hasConnections = false;
        if (preferences != null) {
            hasConnections = preferences.getBoolean(Utils.SAVED_CONNECTIONS, false);
        }
        host = (EditText) findViewById(R.id.editHost);
        port = (EditText) findViewById(R.id.editPort);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        name = (EditText) findViewById(R.id.editname);
        dbName = (EditText) findViewById(R.id.editdbName);
    }

    public void saveConnection(View clicked) {

        String hostText = host.getText().toString();
        String portText = port.getText().toString();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String dbNameText = dbName.getText().toString();
        String nameText = name.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        int connections = preferences.getInt("connections.no", 0);
        connections = connections + 1;
        editor.putString("connection_" + connections, hostText + "," + portText + "," + usernameText + "," + passwordText + "," + dbNameText + "," + nameText);
        editor.commit();


    }

    public void quickConnect(View clicked) {
        String hostText = host.getText().toString();
        String portText = port.getText().toString();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String dbNameText = password.getText().toString();
        new ConnectAsyncTask().execute(hostText, portText,dbNameText, usernameText, passwordText );
    }


    public class ConnectAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Connection con = Utils.connection;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + params[0] + ":" + params[1] + "/" + params[2], params[3], params[4]);
            } catch (Exception f){
                f.printStackTrace();
            }
            if(con!=null){
                return true;
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            if(result) {
                Toast.makeText(getApplicationContext(), "Connection Saved", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Connection SUCCESS, you can write queries now!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConnectionActivity.this, MainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(getApplicationContext(), "Cannot create connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
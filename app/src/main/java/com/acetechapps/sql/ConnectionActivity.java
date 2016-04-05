package com.acetechapps.sql;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.acetechapps.sql.service.ActivityService;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionActivity extends AppCompatActivity  {

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

        Toast .makeText(getApplicationContext(),"Connection Saved", Toast.LENGTH_SHORT).show();


    }

    public void quickConnect(View clicked) {
        String hostText = host.getText().toString();
        String portText = port.getText().toString();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String dbNameText = dbName.getText().toString();
        new ConnectAsyncTask(getApplicationContext(), true).execute(hostText, portText,dbNameText, usernameText, passwordText );
    }



    public class ConnectAsyncTask extends AsyncTask<String, Integer, Boolean> {

        Context applicationContext;
        boolean startMain = false;

        public ConnectAsyncTask(Context context, boolean startMain){
            this.applicationContext = context;
            this.startMain = startMain;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String url = "jdbc:mysql://" + params[0] + ":" + params[1] + "/" + params[2];
                System.out.println(url);
               Utils.connection = DriverManager.getConnection( url, params[3], params[4]);
            } catch (Exception f){
                f.printStackTrace();
            }
            if(Utils.connection!=null){
                return true;
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            if(result) {
                Toast.makeText(getApplicationContext(), "Connection SUCCESS, you can write queries now!", Toast.LENGTH_SHORT).show();
                if(startMain) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            } else{
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        }

        public Context getApplicationContext() {
            return applicationContext;
        }

        public void setApplicationContext(Context applicationContext) {
            this.applicationContext = applicationContext;
        }
    }


}
package com.acetechapps.sql;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bhargavsarvepalli on 05/02/16.
 */
public class ConnectionActivity extends Activity {
    EditText host;
    EditText port;
    EditText username;
    EditText password;
    EditText name;
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
    }

    public void saveConnection(View clicked) {

        String hostText = host.getText().toString();
        String portText = port.getText().toString();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String nameText = name.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        int connections = preferences.getInt("connections.no", 0);
        connections = connections + 1;
        editor.putString("connection_" + connections, hostText + ","+ portText + "," + usernameText + "," + passwordText + "," + nameText);
        editor.commit();

        Toast.makeText(getApplicationContext(), "Connection Saved", Toast.LENGTH_SHORT).show();
    }
}

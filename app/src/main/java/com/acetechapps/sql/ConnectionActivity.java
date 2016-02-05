package com.acetechapps.sql;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by bhargavsarvepalli on 05/02/16.
 */
public class ConnectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        SharedPreferences preferences = getSharedPreferences(Utils.PREFERENCES, 0);
        boolean hasConnections = false;
        if(preferences!=null){
            hasConnections = preferences.getBoolean(Utils.SAVED_CONNECTIONS, false);
        }
    }
}

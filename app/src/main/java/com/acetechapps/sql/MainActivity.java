package com.acetechapps.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.acetechapps.sql.service.ActivityService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    EditText writtenQuery;
    String getQuery;
    SharedPreferences preferences;
    Connection connection;
    TextView textView;

    private Toolbar toolbar;
    DrawerLayout drawerLayout;

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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_queries) {

        } else if (id == R.id.nav_tables) {

        } else if (id == R.id.nav_conneciton) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

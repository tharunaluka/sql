package com.acetechapps.sql;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText writtenQuery;
    String getQuery;
    Connection connection;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiating other App Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        if(Utils.connection!=null){
            connection = Utils.connection;
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

    // Inflate the other appbar menu; also adds items to the action bar if present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_other_menu, menu);
        return true;
    }

    //Other appbar onClickListener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.otherAppbarSettings:
                Toast.makeText(getApplicationContext(),"No Settings Found",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save(View clicked) {
        Toast.makeText(getApplicationContext(), "Query not Saved", Toast.LENGTH_SHORT).show();
    }

    public void clear(View view) {
        getQuery = "Start Typing Query...";
        writtenQuery.setHint(getQuery);
    }

    public void runQuery(View clicked) {
        Intent i = new Intent(this, OutputActivity.class);
        RunQueryTask task = new RunQueryTask(this, i);
        task.execute(writtenQuery.getText().toString());
    }

    public void setViewAndDisplayResult(ResultSet rs) throws Exception{
        Utils.results = rs;
        Intent i = new Intent(this, OutputActivity.class);
        startActivity(i);
    }

    public void openDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    public void menuClick(View view){
        if(view.getId() == R.id.ic_drawer){
            openDrawer();
        }
    }



    public class RunQueryTask extends AsyncTask<String, Integer, Boolean> {

        ResultSet rs;
        Context applicationContext;
        Intent i;

        RunQueryTask(Context applicationContext, Intent i){
            this.applicationContext = applicationContext;
            this.i = i;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (Utils.connection != null) {
                    connection = Utils.connection;
                    Statement stmt = Utils.connection.createStatement();
                    rs = stmt.executeQuery(params[0]);
                    System.out.println("query" +  params[0]);
                    return true;
                } else {
                    System.out.println("query -- no connection " +  params[0]);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if(result) {
                try {
                    Utils.results = rs;
                    applicationContext.startActivity(i);
                } catch (Exception e){
                    e.printStackTrace();
                }

            } else{
                Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
            }
        }

        public void setApplicationContext(Context applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Context getApplicationContext() {
            return applicationContext;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        drawerLayout.closeDrawers();

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_queries) {
            Toast.makeText(this, "Queries Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QueryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_tables) {

            Toast.makeText(this, "Tables Selected", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_conneciton) {

            Toast.makeText(this, "Connection Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListConnectionsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Toast.makeText(this, "Share Selected", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {

            Toast.makeText(this, "About Selected", Toast.LENGTH_SHORT).show();

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
}

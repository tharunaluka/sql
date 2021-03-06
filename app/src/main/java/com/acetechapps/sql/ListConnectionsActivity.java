package com.acetechapps.sql;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acetechapps.sql.db.DbHelper;

/**
 * Created by bhargavsarvepalli on 05/04/16.
 *
 */
public class ListConnectionsActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DbHelper sqlDb;

    Cursor myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_connections);

        // Initiating other App Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.allconnectionstoolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        sqlDb = new DbHelper(this);
        getDataSet();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
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

    void getDataSet(){
        myDataset = sqlDb.getAllConnections();
        if (!(myDataset.moveToFirst()) || myDataset.getCount() ==0){
            Intent intent = new Intent(this, ConnectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Cursor mDataset;
        private int count;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Cursor myDataset) {
            mDataset = myDataset;
            mDataset.moveToFirst();
            count = mDataset.getCount() == 0 ? 0 : mDataset.getCount();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.connection_recycler_item, parent, false);
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectionActivity.ConnectAsyncTask asyncTask = new ConnectionActivity.ConnectAsyncTask(ListConnectionsActivity.this, true);
                    asyncTask.execute(); //hostText, portText,dbNameText, usernameText, passwordText
                }
            });*/
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            mDataset.moveToPosition(position);
            holder.mTextView.setText(mDataset.getString(0) + ". " + mDataset.getString(1));
            holder.mTextView.setTypeface(null, Typeface.BOLD);
            holder.mTextView2.setText("Host: "+mDataset.getString(2)+"\nUsername: "+mDataset.getString(4)+"\nDB Name: "+mDataset.getString(6));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return count;
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.connection_item_name);
            mTextView2 = (TextView)v.findViewById(R.id.connection_item_db_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String name = ((TextView) v.findViewById(R.id.connection_item_name)).getText().toString().replaceAll(".*\\.\\s+","");
            Cursor cursor = sqlDb.getConnectionByName(name);
            cursor.moveToNext();
            cursor.moveToFirst();
            ConnectionActivity.ConnectAsyncTask asyncTask = new ConnectionActivity.ConnectAsyncTask(ListConnectionsActivity.this, true);
            asyncTask.execute(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) ); //hostText, portText,dbNameText, usernameText, passwordText
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getDataSet();
        mAdapter.notifyDataSetChanged();
    }


    public void addConnection(View v){
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivity(intent);
    }
}

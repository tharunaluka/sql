package com.acetechapps.sql;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acetechapps.sql.dbHelper.DbHelper;

/**
 * Created by bhargavsarvepalli on 05/04/16.
 *
 */
public class ListConnectionsActivity extends Activity {

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private DbHelper sqlDb;

        Cursor myDataset;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_connections);
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

    void getDataSet(){
        myDataset = sqlDb.getAll();
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
            holder.mTextView.setText(mDataset.getString(0));
            holder.mTextView2.setText(mDataset.getString(1));
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
            Cursor cursor = sqlDb.getConnectionByName(((TextView) v.findViewById(R.id.connection_item_name)).getText().toString());
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

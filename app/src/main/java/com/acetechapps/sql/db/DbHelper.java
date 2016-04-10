package com.acetechapps.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tharunaluka on 13/03/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String dbName = "sqlDB";
    public static final String queryTableName = "queryTable";
    public static final String connectionsTableName = "connectionsTable";
    public static final String Column1 = "queryID";
    public static final String Column2 = "queryTitle";
    public static final String Column3 = "queryQuery";


    public DbHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + queryTableName + " (" + Column1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column2 + " TEXT, " + Column3 + " TEXT)");
        db.execSQL("create table if not exists " + connectionsTableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name  TEXT unique, host TEXT, port TEXT, user_name TEXT, password TEXT, db_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + queryTableName);
        onCreate(db);
    }

    public boolean insertData(String queryTitle, String queryQuery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column2, queryTitle);
        contentValues.put(Column3, queryQuery);
        long result = db.insert(queryTableName, null, contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     *
     * @param params
     * 1. name of connection
     * 2. host
     * 3. port
     * 4. username
     * 5. password
     * 6. dbname
     * @return
     */
    public boolean insertNewConnection(String... params) throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", params[0]);
        contentValues.put("host", params[1]);
        contentValues.put("port", params[2]);
        contentValues.put("user_name", params[3]);
        contentValues.put("password", params[4]);
        contentValues.put("db_name", params[5]);

        long result = db.insertOrThrow(connectionsTableName, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor showAllQueries() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+queryTableName,null);
        return res;
    }

    public Cursor getAllConnections() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+connectionsTableName,null);
        return res;
    }

    public Cursor getConnectionByName(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT  host, port, db_name, user_name, password FROM "+connectionsTableName + " where name='"+ s +"'",null);
        return res;
    }
}

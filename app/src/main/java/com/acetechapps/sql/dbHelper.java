package com.acetechapps.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tharunaluka on 13/03/16.
 */
public class dbHelper extends SQLiteOpenHelper {

    public static final String dbName = "sqlDB";
    public static final String queryTableName = "queryTable";
    public static final String Column1 = "queryID";
    public static final String Column2 = "queryTitle";
    public static final String Column3 = "queryQuery";


    public dbHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + queryTableName + " (" + Column1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column2 + " TEXT, " + Column3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ queryTableName);
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

    public Cursor showAllQueries() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+queryTableName,null);
        return res;
    }
}

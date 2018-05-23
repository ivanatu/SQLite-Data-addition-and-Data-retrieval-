package com.example.baron.mypresentationapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by baron on 03/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "contacts";
    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "mobile";
    private static final String COL4 = "email";

    /**
     * Database helper constructor
     * */
    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        String createTable = " CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j){
        db.execSQL(" DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * copies all data to the database
     * */
    public boolean addData(String name , String mobile, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, mobile);
        contentValues.put(COL4, email);

        Log.d(TAG, "addData: Adding" + name+ mobile + email+ "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null , contentValues);

        if(result == -1){
           return false;
        }else{
            return true;
        }

    }

    /**
     * returns data from the database
     * */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}

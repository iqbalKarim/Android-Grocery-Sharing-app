package com.iqbal.karim.ahmed.salik.project.databaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperItems extends SQLiteOpenHelper {
    public static String DATABASE_NAME="items.db";
    public static int  DATABASE_VERSION = 1;
    SQLiteDatabase db;

    public DatabaseHelperItems(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();//Writable and Readable mode
        Log.d("DATABASE OPERATIONS","Connection Provided");
    }

    public void close(){
        if(db != null && db.isOpen()) {
            db.close();
            Log.d("DATABASE OPERATIONS", "CLOSE");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ItemsTable.CREATE_TABLE_ITEMS);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DATABASE OPERATIONS","OnCreate, table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL(ItemsTable.DROP_TABLE_ITEMS);
        }catch (SQLException e){
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase);
        Log.d("DATABASE OPERATIONS","onUpgrade,  table dropped, old version "+oldVersion+" new version "+newVersion);
    }

    public Cursor getAllRecords(String tableName, String[] columns   ){
        Cursor cursor = db.query(tableName, columns, null, null, null, null, null );
        Log.d("DATABASE OPERATIONS", "GET THE RECORDS");
        return cursor;
    }

    public Cursor getSomeRecords(String tableName, String[] columns, String where ){
        Cursor cursor = db.query(tableName, columns, where, null, null, null, null);
        Log.d("DATABASE OPERATIONS", "GET ALL RECORDS WITH WHERE CLAUSE");
        return cursor;
    }

    public boolean insert(String tableName, ContentValues contentValues){
        Log.d("DATABASE OPERATIONS", "INSERT DONE");
        return db.insert(tableName, null, contentValues)>0;
    }

    public boolean update(String tableName, ContentValues contentValues, String whereCondition){
        Log.d("DATABASE OPERATIONS", "UPDATE DONE");
        return db.update(tableName,contentValues,whereCondition,null)>0;
    }

    public boolean delete(String tableName, String whereCondition){
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        return db.delete(tableName, whereCondition, null)>0;
    }

    public boolean deleteAll(String tableName){
        return db.delete(tableName, null, null) > 0;
    }
}

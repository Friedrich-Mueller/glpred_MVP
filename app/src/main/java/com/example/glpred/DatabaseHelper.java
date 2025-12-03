package com.example.glpred;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GLP.db";
    public static final String TABLE_NAME = "diary_table";
    public static final String COL_1 = "DATE";
    public static final String COL_2 = "TIME";
    public static final String COL_3 = "GLUCOSELEVEL";
    public static final String COL_4 = "CARBUNITS";
    public static final String COL_5 = "BOLUS";
    public static final String COL_6 = "BASIS";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(@androidx.annotation.NonNull SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (DATE STRING, TIME STRING, GLUCOSELEVEL INTEGER, CARBUNITS FLOAT, BOLUS INTEGER, BASIS INTEGER) ");
    }

    @Override
    public void onUpgrade(@androidx.annotation.NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String time, String glucoselevel, String carbunits, String bolus, String basis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,date);
        contentValues.put(COL_2,time);
        contentValues.put(COL_3,Integer.parseInt(glucoselevel));
        contentValues.put(COL_4,Float.valueOf(carbunits));
        contentValues.put(COL_5,Integer.parseInt(bolus));
        contentValues.put(COL_6,Integer.parseInt(basis));
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;

    }
}
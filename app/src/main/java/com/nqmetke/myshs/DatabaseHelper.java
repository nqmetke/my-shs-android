package com.nqmetke.myshs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nqmet on 9/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "homework.db";
    public static final String TABLE_NAME = "homework_table";
    public static final String COL_ID = "ID";
    public static final String COL_PARENT_COURSE = "PARENT_COURSE";
    public static final String COL_HOMEWORK_TITLE = "HOMEWORK_TITLE";
    public static final String COL_HOMEWORK_DESCRIPTION = "HOMEWORK_DESCRIPTION";
/*    public static final String COL_HOMEWORK_REMINDER_DATE = "HOMEWORK_REMINDER_DATE";
    public static final String COL_HOMEWORK_REMINDER_TIME = "HOMEWORK_REMINDER_TIME";*/


    public DatabaseHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_PARENT_COURSE+" TEXT, "+COL_HOMEWORK_TITLE+" TEXT, " +COL_HOMEWORK_DESCRIPTION+ " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String parent_course, String homework_title, String homework_description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PARENT_COURSE, parent_course);
        contentValues.put(COL_HOMEWORK_TITLE, homework_title);
        contentValues.put(COL_HOMEWORK_DESCRIPTION, homework_description);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }
}


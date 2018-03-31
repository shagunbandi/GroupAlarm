package com.justapp.groupalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class AlarmDBManager extends SQLiteOpenHelper{

    final String TAG = "SHAGUN";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarms.db";
    public static final String TABLE_ALARMS = "alarm";

    // Columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_INFO = "info";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MIN = "minutes";
    public static final String COLUMN_STATUS = "alarm_status";

    public AlarmDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ALARMS + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LABEL + " TEXT, " +
                COLUMN_INFO + " TEXT, " +
                COLUMN_HOUR + " INTEGER, " +
                COLUMN_MIN + " INTEGER, " +
                COLUMN_STATUS + " BOOLEAN " +

                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_ALARMS;
        db.execSQL(query);
        onCreate(db);
    }

    // Add a new row
    public void add_alarm(Alarms alarm) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LABEL, alarm.get_label());
        values.put(COLUMN_INFO, alarm.get_info());
        values.put(COLUMN_HOUR, alarm.get_hour());
        values.put(COLUMN_MIN, alarm.get_min());
        values.put(COLUMN_STATUS, alarm.get_alarm_status());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ALARMS, null, values);

        Log.i(TAG, "Row Added");
    }

    public void update_alarm(Alarms alarm, String prevAlarmLabel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LABEL, alarm.get_label());
        values.put(COLUMN_INFO, alarm.get_info());
        values.put(COLUMN_HOUR, alarm.get_hour());
        values.put(COLUMN_MIN, alarm.get_min());
        values.put(COLUMN_STATUS, alarm.get_alarm_status());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_ALARMS, values, COLUMN_LABEL + "=\"" + prevAlarmLabel + "\"", null);
        Log.i(TAG, "Updated");
    }

    public void delete_alarm(String alarmLabel){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ALARMS + " WHERE " + COLUMN_LABEL + " =\"" + alarmLabel + "\"; ";
        db.execSQL(query);

        Log.i(TAG, alarmLabel + " Deleted from the Table");
    }

    public String[] get_all_labels() {

        ArrayList<String> labelList = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARMS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        if (c!=null && c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(COLUMN_LABEL)) != null) {
                    labelList.add(c.getString(c.getColumnIndex(COLUMN_LABEL)));
                }
            } while (c.moveToNext());
        }

        db.close();

//        labelList.add("1");
//        labelList.add("2");
//        labelList.add("3");

        return labelList.toArray(new String[0]);
    }

    public HashMap<String, Object> get_row_with_label(String label){

//        TODO Currently return only the first item with that label

        HashMap<String, Object> map= new HashMap<String, Object>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_LABEL + "=\"" + label + "\";";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            map.put(COLUMN_ID, cursor.getInt(0));
            map.put(COLUMN_LABEL, cursor.getString(1));
            map.put(COLUMN_INFO, cursor.getString(2));
            map.put(COLUMN_HOUR, cursor.getString(3));
            map.put(COLUMN_MIN, cursor.getString(4));
            map.put(COLUMN_STATUS, cursor.getInt(5) > 0);
        }

        return map;
    }




}

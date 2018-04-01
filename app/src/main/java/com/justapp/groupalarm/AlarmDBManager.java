package com.justapp.groupalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    public static final String COLUMN_TIME = "time_in_millis";

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
                COLUMN_STATUS + " BOOLEAN, " +
                COLUMN_TIME + " INTEGER " +
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
        ContentValues values = get_values_from_Alarm(alarm);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ALARMS, null, values);
        Log.i(TAG, "Row Added");
    }

    public void update_alarm(Alarms alarm, String prevAlarmLabel) {
        ContentValues values = get_values_from_Alarm(alarm);
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

    private ContentValues get_values_from_Alarm(Alarms alarm){

        ContentValues values = new ContentValues();
        values.put(COLUMN_LABEL, alarm.get_label());
        values.put(COLUMN_INFO, alarm.get_info());
        values.put(COLUMN_HOUR, alarm.get_hour());
        values.put(COLUMN_MIN, alarm.get_min());
        values.put(COLUMN_STATUS, alarm.get_alarm_status());
        values.put(COLUMN_TIME, alarm.get_timeinmillis());

        return values;
    }


    public Alarms[] get_all_alarms() {
        ArrayList<Alarms> alarmList = new ArrayList<Alarms>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARMS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);


        if (c!=null && c.moveToFirst()) {
            do {
                Alarms alarm = get_alarm_from_cursor(c);
                alarmList.add(alarm);

            } while (c.moveToNext());
        }

        db.close();

        return alarmList.toArray(new Alarms[0]);


    }

    public Alarms get_alarm_with_label(String label) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_LABEL + "=\"" + label + "\";";
        Cursor cursor = db.rawQuery(query, null);
        Alarms alarm  = new Alarms();
        if (cursor.moveToFirst())
        {
            alarm = get_alarm_from_cursor(cursor);
        }
        return alarm;
    }


    private Alarms get_alarm_from_cursor(Cursor cursor){
        Alarms alarm = new Alarms();

        alarm.set_info(cursor.getString(cursor.getColumnIndex(COLUMN_INFO)));
        alarm.set_label(cursor.getString(cursor.getColumnIndex(COLUMN_LABEL)));
        alarm.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        alarm.set_min(cursor.getString(cursor.getColumnIndex(COLUMN_MIN)));
        alarm.set_hour(cursor.getString(cursor.getColumnIndex(COLUMN_HOUR)));
        alarm.set_alarm_status(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) > 0);
        alarm.set_timeinmillis(cursor.getLong(cursor.getColumnIndex(COLUMN_TIME)));

        return alarm;
    }


    //    NOT USED
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
        return labelList.toArray(new String[0]);
    }


    //    NOT USED
    public HashMap<String, Object> get_row_with_label(String label){

        HashMap<String, Object> map= new HashMap<String, Object>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_LABEL + "=\"" + label + "\";";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            put_values_in_map(map, cursor);
        }

        return map;
    }

    //    NOT USED
    private void put_values_in_map(HashMap<String, Object> map, Cursor cursor){
        map.put(COLUMN_ID, cursor.getInt(0));
        map.put(COLUMN_LABEL, cursor.getString(1));
        map.put(COLUMN_INFO, cursor.getString(2));
        map.put(COLUMN_HOUR, cursor.getString(3));
        map.put(COLUMN_MIN, cursor.getString(4));
        map.put(COLUMN_STATUS, cursor.getInt(5) > 0);
        map.put(COLUMN_TIME, cursor.getLong(6));
    }

    //    NOT USED
    private Alarms get_alarmObject_from_hashmap(HashMap<String, Object> map){
        Alarms alarm = new Alarms();
        alarm.set_info((String) map.get(COLUMN_INFO));
        alarm.set_label((String) map.get(COLUMN_LABEL));
        alarm.set_id((int) map.get(COLUMN_ID));
        alarm.set_min((String) map.get(COLUMN_MIN));
        alarm.set_hour((String) map.get(COLUMN_HOUR));
        alarm.set_alarm_status((Boolean) map.get(COLUMN_STATUS));
        return alarm;
    }



}

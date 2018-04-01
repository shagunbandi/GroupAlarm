package com.justapp.groupalarm;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class Alarms {

    private int _id;
    private String _label;
    private String _info;
    private boolean _alarm_status;
    private long _timeinmillis;


    public Alarms() {
        _label = "";
        _info = "";
        _alarm_status = false;
        _timeinmillis = Calendar.getInstance().getTimeInMillis();
    }

    //Setters
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_label(String _label) {
        this._label = _label;
    }

    public void set_info(String _info) {
        this._info = _info;
    }

    public void set_timeinmillis(long _timeinmillis) {
        this._timeinmillis = _timeinmillis;
    }

    public void set_alarm_status(boolean _alarm_status) {
        this._alarm_status = _alarm_status;
    }

    //Getters
    public int get_id() {
        return _id;
    }

    public String get_label() {
        return _label;
    }

    public String get_info() {
        return _info;
    }

    public long get_timeinmillis() {
        return _timeinmillis;
    }

    public boolean get_alarm_status() {
        return _alarm_status;
    }

    //Other Functions
    public int get_hour() {
        return (int) (TimeUnit.MILLISECONDS.toHours(_timeinmillis) % 24);
    }

    public int get_min() {
        return (int) (TimeUnit.MILLISECONDS.toMinutes(_timeinmillis) % 60);
    }

    public String get_time_AMPM(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(_timeinmillis);
    }

    public String get_OnOff() {
        if (_alarm_status)
            return "On";
        else
            return "Off";
    }
}

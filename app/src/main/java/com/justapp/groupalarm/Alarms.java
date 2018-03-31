package com.justapp.groupalarm;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class Alarms {

    private int _id;
    private String _label;
    private String _info;
    private String _hour;
    private String _min;
    private long _timeinmillis;
    private boolean _alarm_status;


    public Alarms() {
        _label = "";
        _info = "";
        _hour=  "";
        _min = "";
        _alarm_status = false;
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

    public void set_hour(String _hour) {
        this._hour = _hour;
    }

    public void set_min(String _min) {
        this._min = _min;
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

    public String get_hour() {
        return _hour;
    }

    public String get_min() {
        return _min;
    }

    public long get_timeinmillis() {
        return _timeinmillis;
    }

    public boolean get_alarm_status() {
        return _alarm_status;
    }


}

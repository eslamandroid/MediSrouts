package com.app.medisrout.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedData {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public SharedData(Context context) {
        preferences = context.getSharedPreferences("MediSrout", Context.MODE_PRIVATE);
        editor = preferences.edit();


    }

    public <V> V getValue(ReturnValue returnValue, String key) {
        V vs = null;
        switch (returnValue) {
            case INT:
                vs = (V) Integer.valueOf(preferences.getInt(key, 0));
                break;
            case STRING:
                vs = (V) new String(preferences.getString(key, ""));
                break;
            case LONG:
                vs = (V) Long.valueOf(preferences.getLong(key, 0L));
                break;
            case BOOLEAN:
                vs = (V) Boolean.valueOf(preferences.getBoolean(key, false));
                break;

        }
        return vs;
    }

    public <V> void putValue(String key, V value) {
        if (value.getClass() == Integer.class) {
            int i = ((Integer) value);
            editor.putInt(key, i);
            editor.apply();
        } else if (value.getClass() == String.class) {
            String i = ((String) value);
            editor.putString(key, i);
            editor.apply();

        } else if (value.getClass() == Float.class) {
            float x = ((Float) value);
            editor.putFloat(key, x);
            editor.apply();
        } else if (value.getClass() == Boolean.class) {
            boolean x = ((Boolean) value);
            editor.putBoolean(key, x);
            editor.apply();
        } else if (value.getClass() == Long.class) {
            long x = ((Long) value);
            editor.putLong(key, x);
            editor.apply();
        }
    }

    public void clear() {
        editor.clear().apply();
    }


    public enum ReturnValue {
        INT, STRING, LONG, BOOLEAN
    }

}

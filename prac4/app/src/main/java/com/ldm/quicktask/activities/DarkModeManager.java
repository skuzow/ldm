package com.ldm.quicktask.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class DarkModeManager {
    private final String DARK_MODE_KEY = "DarkMode";

    private final SharedPreferences sharedPreferences;

    public DarkModeManager(Context context) {
        sharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
    }

    public void setDarkMode(Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(DARK_MODE_KEY, value);
        editor.apply();
    }

    public Boolean isDarkModeStored() {
        return sharedPreferences.contains(DARK_MODE_KEY);
    }

    public Boolean getDarkMode() {
        return sharedPreferences.getBoolean(DARK_MODE_KEY, false);
    }
}

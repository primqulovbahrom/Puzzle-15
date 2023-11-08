package uz.gita.puzzle15.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LocalStorage {
    private static LocalStorage instance = null;

    private Context context;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private LocalStorage(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LocalStorage(context);
            return instance;
        }
        return instance;
    }


    public void setItemNumber(String key, String number) {
        editor.putString(key, number).apply();
    }

    public String getItemNumber(String key) {
        return preferences.getString(key, "pp");
    }

    public void setIsTrue(Boolean isTrue) {
        editor.putBoolean("BOOL", isTrue).apply();
    }

    public Boolean getIsTrue() {
        return preferences.getBoolean("BOOL", false);
    }

    public void setTimeStr(String str) {
        editor.putString("STR", str).apply();
    }

    public String getTimeStr() {
        return preferences.getString("STR", "00:00");
    }

    public void setTimeCount(int timeCount) {
        editor.putInt("TIME", timeCount).apply();
    }

    public int getTimeCount() {
        return preferences.getInt("TIME", 0);
    }

    public void setCounter(int counter) {
        editor.putInt("COUNTER", counter).apply();
    }

    public int getCounter() {
        return preferences.getInt("COUNTER", 0);
    }

    public void setIsAudioOn(Boolean isTrue) {
        editor.putBoolean("BOOL2", isTrue).apply();
    }

    public Boolean getIsAudioOn() {
        return preferences.getBoolean("BOOL2", false);
    }
}

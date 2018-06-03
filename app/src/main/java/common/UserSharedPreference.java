package common;

import android.content.SharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

public class UserSharedPreference {
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setAccount(Context context, String userName, String password)
    {
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.putString(USERNAME, userName);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static void removeAccount(Context context){
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.remove(USERNAME);
        editor.remove(PASSWORD);
        editor.commit();
    }

    public static String getUserName(Context context)
    {
        return getSharedPreferences(context).getString(USERNAME, "");
    }

    public static String getPassword(Context context)
    {
        return getSharedPreferences(context).getString(PASSWORD, "");
    }
}

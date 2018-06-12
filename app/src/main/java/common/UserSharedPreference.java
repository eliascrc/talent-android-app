package common;

import android.content.SharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

public class UserSharedPreference {
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String TOKEN = "token";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String token)
    {
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static void removeToken(Context context){
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.remove(TOKEN);
        editor.commit();
    }

    public static String getToken(Context context)
    {
        return getSharedPreferences(context).getString(TOKEN, "");
    }

}

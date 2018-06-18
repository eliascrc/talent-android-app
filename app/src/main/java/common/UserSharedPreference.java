package common;

import android.content.SharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * This class stores values that are need to be persistent even
 * when the application shuts down.
 *
 * @author Otto Mena Kikut
 */
public class UserSharedPreference {
    static final String TOKEN = "token";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String token)
    {
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static void removeToken(Context context){
        SharedPreferences account = getSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = account.edit();
        editor.remove(TOKEN);
        editor.apply();
    }

    public static String getToken(Context context)
    {
        return getSharedPreferences(context).getString(TOKEN, "");
    }

}

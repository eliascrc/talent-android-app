package common;


import android.content.Context;
import android.os.Bundle;

import icepick.State;

import static icepick.Icepick.restoreInstanceState;
import static icepick.Icepick.saveInstanceState;

/**
 * This class storing values that are required across the application lifetime
 * so they are accessible at any time they are needed.
 *
 * @author Renato Mainieri Sáenz.
 */
public class SessionStorage {

    private static SessionStorage instance;

    @State
    protected String cookieValue;

    private SessionStorage(){}

    public static synchronized SessionStorage getInstance(){
        if(instance == null){
            instance = new SessionStorage();
        }
        return instance;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public boolean isLoggedIn(Context context){
        boolean loggedIn;
        if(UserSharedPreference.getToken(context).equals("")){
            loggedIn = false;
        }
        else{
            loggedIn = true;
        }
        return loggedIn;
    }
    /**
     * Restore and save a old state of the cookieValue.
     *
     * @param savedInstanceState, the old state to restore of the cookieValue.
     */
    public void restoreState(Bundle savedInstanceState) {
        restoreInstanceState(this, savedInstanceState);
    }

    /**
     * Save a new state in the cookieValue.
     *
     * @param outState, the new state to save in the cookieValue.
     */
    public void saveState(Bundle outState) {
        saveInstanceState(this, outState);
    }
}

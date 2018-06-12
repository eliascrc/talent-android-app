package common;


import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;

import javax.inject.Singleton;

import icepick.State;

import static icepick.Icepick.restoreInstanceState;
import static icepick.Icepick.saveInstanceState;

/**
 * This class storing values that are required across the application lifetime
 * so they are accessible at any time they are needed.
 *
 * @author Renato Mainieri Sáenz.
 */
@Singleton
public class SessionStorage {

    @State
    protected String cookieValue;

    @State
    protected String token;

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getToken(){
        if (token != null){
            return token;
        }
        return "";
    }

    public void setToken(String tokenString){
        this.token = tokenString;
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

package cr.talent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
public class SessionStorage extends AppCompatActivity {

    @State
    String cookieValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(this, outState);
    }
}

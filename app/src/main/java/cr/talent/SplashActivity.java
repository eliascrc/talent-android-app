package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import common.SessionStorage;
import common.UserSharedPreference;
import request.ServiceCallback;



/**
 * This class starts the splash activity for the Talent! application. When the theme(logo) is displayed it will route the
 * user to the login activity. Notice that the login activity design is not the final design.
 *
 * @author Josué David Cubero Sánchez.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        String token = UserSharedPreference.getToken(SplashActivity.this);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        Log.d(TAG, token);
        if (!SessionStorage.getInstance().isLoggedIn(SplashActivity.this)){
            Intent startActivity = new Intent(SplashActivity.this, LandingViewActivity.class);
            SplashActivity.this.startActivity(startActivity);
        }
        else{
            Intent startActivity = new Intent(SplashActivity.this, DashboardActivity.class);
            SplashActivity.this.startActivity(startActivity);
        }
    }
}

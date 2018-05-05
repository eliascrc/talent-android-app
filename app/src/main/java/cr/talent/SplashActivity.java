package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class starts the splash activity for the Talent! application. When the theme(logo) is displayed it will route the
 * user to the login activity. Notice that the login activity design is not the final design.
 *
 * @author Josué David Cubero Sánchez.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(loginActivity);
    }
}

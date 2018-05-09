package cr.talent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This class displays Talent’s Landing View layout, with the option to Sign up and Log in.
 *
 * @author Renato Mainieri Sáenz.
 */
public class LandingViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        getSupportActionBar().hide();
    }
}
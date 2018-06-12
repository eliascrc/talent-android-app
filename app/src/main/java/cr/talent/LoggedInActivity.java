package cr.talent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import common.SessionStorage;
import common.UserSharedPreference;

/**
 * This class only exists to let us know that we are logged in.
 *
 */
public class LoggedInActivity extends AppCompatActivity {

    private Button logoutButton;

    private SessionStorage sessionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        sessionStorage = new SessionStorage();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_logged_in);

        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSharedPreference.removeToken(LoggedInActivity.this);
                Intent landingViewActivity = new Intent(LoggedInActivity.this, LandingViewActivity.class);
                LoggedInActivity.this.startActivity(landingViewActivity);
            }
        });
    }
}

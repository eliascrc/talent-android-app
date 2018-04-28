package cr.talent;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;


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
        setContentView(R.layout.activity_splash);

        View view = findViewById(R.id.item_logo);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = (int) (metrics.widthPixels * 0.85);
        int height = (int) (metrics.heightPixels * 0.40);
        view.setLayoutParams(new ActionBar.LayoutParams(width, height));

        Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(loginActivity);
    }
}

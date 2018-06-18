package cr.talent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity displays the about us view.
 *
 * @author Otto Mena Kikut
 */
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about_us);
    }
}

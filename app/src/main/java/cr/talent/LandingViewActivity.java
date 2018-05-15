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

/**
 * This class displays Talent’s Landing View layout, with the option to Sign up and Log in.
 *
 * @author Renato Mainieri Sáenz.
 */
public class LandingViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private Button signUpButton;
    private Button logInButton;
    private TextView exploreAppText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        getSupportActionBar().hide();

        this.viewPager = findViewById(R.id.landing_view_pager);
        this.linearLayout = findViewById(R.id.landing_view_index);
        final ScrollView SCROLLVIEW = findViewById(R.id.landing_scroll_view);
        SCROLLVIEW.post(new Runnable() {
            @Override
            public void run() {
                SCROLLVIEW.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        ViewPagerFragment viewPagerFragment = new ViewPagerFragment(this, linearLayout);
        this.viewPager.setAdapter(viewPagerFragment);

        this.signUpButton = findViewById(R.id.sign_up_button);
        this.logInButton = findViewById(R.id.log_in_button);
        this.exploreAppText = findViewById(R.id.explore_the_app);

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //TODO Go to Sign Up Activity
            }
        });

        this.logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO Go to Log in Activity
            }
        });

        this.exploreAppText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(LandingViewActivity.this, ContentOptionActivity.class);
                LandingViewActivity.this.startActivity(startActivity);
            }
        });
    }
}
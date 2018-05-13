package cr.talent;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * This class displays Talent’s Landing View layout, with the option to Sign up and Log in.
 *
 * @author Renato Mainieri Sáenz.
 */
public class LandingViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.landing_view_pager);
        this.linearLayout = findViewById(R.id.landing_view_index);
        final ScrollView SCROLLVIEW = findViewById(R.id.landing_scroll_view);
        SCROLLVIEW.post(new Runnable() {
            @Override
            public void run() {
                SCROLLVIEW.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        ViewPagerFragment viewPagerFragment = new ViewPagerFragment(this, linearLayout);
        viewPager.setAdapter(viewPagerFragment);
    }
}
package cr.talent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * This class is for accessing to Talent! Terms of Service or Privacy Policy.
 *
 * @author Renato Mainieri Sáenz.
 */

public class ContentOptionActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_option);
        getSupportActionBar().hide();

        Button termOfServiceButton = findViewById(R.id.option_terms_of_service);
        Button privacyPolicyButton = findViewById(R.id.option_privacy_policy);

        termOfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(ContentOptionActivity.this, TermsOfServiceActivity.class);
                ContentOptionActivity.this.startActivity(startActivity);
            }
        });

        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(ContentOptionActivity.this, PrivacyPolicyActivity.class);
                ContentOptionActivity.this.startActivity(startActivity);
            }
        });
    }
}

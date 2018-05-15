package cr.talent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by b54076 on 14/05/2018.
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

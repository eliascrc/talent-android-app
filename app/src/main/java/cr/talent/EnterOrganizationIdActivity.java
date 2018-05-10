package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The screen in which the user enters the id of the organization they want to sign in to.
 *
 * @author Fabián Roberto Leandro.
 */

public class EnterOrganizationIdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_organization_id);
    }

    public void enterOrganizationId(View view) {
        Intent signInActivity = new Intent(EnterOrganizationIdActivity.this, SignInActivity.class);
        EnterOrganizationIdActivity.this.startActivity(signInActivity);
    }
}
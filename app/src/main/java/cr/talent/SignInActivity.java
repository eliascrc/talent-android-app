package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * The screen in which the user signs in to an organization
 *
 * @author Fabián Roberto Leandro.
 */

public class SignInActivity extends AppCompatActivity {
    private TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        forgotPasswordTextView = findViewById(R.id.tv_no_account_sign_up);

        // Makes the "Sign Up" part of the TextView clickable
        SpannableString noAccountSignIn = new SpannableString(getString(R.string.label_no_account_sign_up));
        noAccountSignIn.setSpan(new ClickableSpan(){
            // Anonymous class that extends ClickableSpan
            // Need this to start the sign up activity in the custom implementation of onClick()
            @Override
            public void onClick(View textView) {
                // start the sign up activity here
                Intent signUpActivity = new Intent(SignInActivity.this, SignUpFirstStepActivity.class);
                SignInActivity.this.startActivity(signUpActivity);
            }
        }, 23,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgotPasswordTextView.setMovementMethod(LinkMovementMethod.getInstance());
        forgotPasswordTextView.setText(noAccountSignIn);
    }

    public void startForgotPasswordActivity(View view){
        Intent forgotPasswordActivity = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        SignInActivity.this.startActivity(forgotPasswordActivity);
    }
}

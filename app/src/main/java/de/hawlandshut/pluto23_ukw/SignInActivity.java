package de.hawlandshut.pluto23_ukw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize UI Views
        mEditTextEmail = findViewById(R.id.sign_in_email);
        mEditTextPassword = findViewById(R.id.sign_in_password);
        mButtonSignIn = findViewById(R.id.sign_in_button_sign_in);
        mButtonResetPassword = findViewById(R.id.sign_in_button_reset_password);
        mButtonCreateAccount = findViewById(R.id.sign_in_button_create_account);

        // Listener setzen
        mButtonCreateAccount.setOnClickListener( this );
        mButtonSignIn.setOnClickListener( this );
        mButtonResetPassword.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button_sign_in:
                doSignIn();
                return;

            case R.id.sign_in_button_reset_password:
                doResetPassword();
                return;

            case R.id.sign_in_button_create_account:
                doCreateAccount();
                return;
        }
    }

    private void doCreateAccount() {
        Toast.makeText(getApplicationContext(), "You pressed CreateAccount.", Toast.LENGTH_LONG).show();
    }

    private void doResetPassword() {
        Toast.makeText(getApplicationContext(), "You pressed Reset Password.", Toast.LENGTH_LONG).show();
    }

    private void doSignIn() {
        Toast.makeText(getApplicationContext(), "You pressed SignIn.", Toast.LENGTH_LONG).show();
    }

}
package de.hawlandshut.pluto23_ukw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hawlandshut.pluto23_ukw.test.Testdata;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "xx SignInActivitity";

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

        // TODO: Remove in final version
        // Testdaten
        mEditTextPassword.setText("123456");
        mEditTextEmail.setText("fhgreipl@gmail.com");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // If there is no current user, we return from CreateAccount,
        // and then we go home...
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            finish();
        }
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
        Intent intent = new Intent(getApplication(), CreateAccountActivity.class);
        startActivity(intent);
    }

    private void doSignIn() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Kann nicht vorkommen! Trotzdem, zur Sicherheit....
        if (user != null){
            msg = "You should never this. (Fatal)";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }

        String email, password;
        email = mEditTextEmail.getText().toString();
        password = mEditTextPassword.getText().toString();
        // TODO: Check e-mail and password

        FirebaseAuth.getInstance().signInWithEmailAndPassword( email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg;
                        if (task.isSuccessful()){
                            msg = "Your are signed in.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            // TODO: Fehlertext raus!
                            msg = "Sign in failed: " + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void doResetPassword() {
        String email;
        email = mEditTextEmail.getText().toString();
        // TODO: Check E-Mailadress!
        FirebaseAuth.getInstance().sendPasswordResetEmail( email )
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg;
                        if (task.isSuccessful()) {
                            msg = "E-Mail sent.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        } else {
                            // TODO: Fehlertext aus der Message entfernen
                            msg = "Sending mail failed: " + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            Log.d(TAG, msg);
                        }
                    }
                });
    }

}
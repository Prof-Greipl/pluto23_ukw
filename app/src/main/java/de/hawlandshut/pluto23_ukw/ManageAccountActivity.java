package de.hawlandshut.pluto23_ukw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "xx ManageAccountAct.";

    TextView mTextViewMail;
    TextView mTextViewAccountVerified;
    TextView mTextViewId;

    Button mButtonSignOut;
    Button mButtonSendActivationMail;
    Button mButtonDeleteAccount;

    EditText mEditTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        mTextViewMail = findViewById( R.id.manage_account_email);
        mTextViewAccountVerified = findViewById( R.id.manage_account_account_verified);
        mTextViewId = findViewById( R.id.manage_account_id);

        mButtonSignOut = findViewById( R.id.manage_account_button_sign_out);
        mButtonSendActivationMail = findViewById( R.id.manage_account_button_send_activation_mail);

        mEditTextPassword = findViewById( R.id.manage_account_password);
        mButtonDeleteAccount = findViewById( R.id.manage_account_button_delete_account);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user ==null){
            String msg;
            msg ="Fatal. You should never see this.";
            mTextViewMail.setText(msg);
            mTextViewId.setText(msg);
            mTextViewAccountVerified.setText(msg);
        } else {
            mTextViewMail.setText("E-Mail   : " + user.getEmail() );
            mTextViewId.setText("Techn.Id : " + user.getUid());
            mTextViewAccountVerified.setText("Verfied  : " + user.isEmailVerified());
        }

        // Register Listeners
        mButtonSignOut.setOnClickListener( this );
        mButtonDeleteAccount.setOnClickListener( this );
        mButtonSendActivationMail.setOnClickListener( this );

        // TODO: Remove Testdata
        mEditTextPassword.setText("123456");

    }

    @Override
    public void onClick(View view) {
        switch( view.getId() ){
            case R.id.manage_account_button_sign_out:
                doSignOut();
                return;

            case R.id.manage_account_button_send_activation_mail:
                doSendActivationMail();
                return;

            case R.id.manage_account_button_delete_account:
                doReauthAndDeleteAccount();
        }
    }

    private void doReauthAndDeleteAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email, password;
        email = user.getEmail();
        password = mEditTextPassword.getText().toString();

        if (password == null || password.equals("")){
            String msg = "Wrong or missing password.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(email,password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            String msg = "Reauth worked.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            doDeleteAccount();
                        }
                       else {
                            String msg = "Reauth failed!!!!!";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void doDeleteAccount() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg;
                if (task.isSuccessful()){
                    msg = "Account deleted.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    // TODO: Remove errormessage
                    msg = "Deletion failed: " + task.getException();
                    Log.d(TAG, msg);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void doSendActivationMail() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg;
                if (task.isSuccessful()){
                    msg = "Mail sent.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else {
                    // TODO: Remove Errortext
                    msg = "Sending mail failed: " + task.getException();
                    Log.d(TAG, msg);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void doSignOut() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            msg = "User signed out";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
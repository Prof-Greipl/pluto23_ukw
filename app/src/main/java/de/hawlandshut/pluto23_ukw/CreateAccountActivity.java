package de.hawlandshut.pluto23_ukw;

import android.os.Bundle;
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

import de.hawlandshut.pluto23_ukw.test.Testdata;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx CreateAccountAct.";

    EditText mEditTextEmail;
    EditText mEditTextPassword;
    EditText mEditTextPassword1;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEditTextEmail = findViewById(R.id.createAccountEditTextEmail);
        mEditTextPassword = findViewById(R.id.createAccountEditTextPassword);
        mEditTextPassword1 = findViewById(R.id.createAccountEditTextPassword1);
        mButtonCreateAccount = findViewById(R.id.createAccountButtonCreateAccount);
        mButtonCreateAccount.setOnClickListener(this);

        // TODO Remove Testdata
        // Testdaten
        mEditTextEmail.setText("fhgreipl@gmail.com");
        mEditTextPassword.setText("123456");
        mEditTextPassword1.setText("123456");
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.createAccountButtonCreateAccount:
                doCreateAccount();
                return;
        }
    }

    private void doCreateAccount() {

        String email, password, password1;
        email = mEditTextEmail.getText().toString();
        password = mEditTextPassword.getText().toString();
        password1 = mEditTextPassword1.getText().toString();

        // TODO: Check E-Mail,
        if (!password1.equals( password)){
            String msg = "Passwords do not match";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length()< 6){
            String msg = "Password too short";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }


        //    showProgressBar();
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg;
                        if (task.isSuccessful()) {
                            msg = "User created";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            finish();
                            //  hideProgressBar();
                        } else {
                            msg = "User creation failed:" + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            //  hideProgressBar();
                        }
                    }
                });
    }
}
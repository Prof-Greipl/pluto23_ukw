package de.hawlandshut.pluto23_ukw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hawlandshut.pluto23_ukw.test.Testdata;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "xx MainActivitity";

    CustomAdapter mAdapter;
    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called.");

        mProgressBar = findViewById( R.id.progressBar);
        hideProgressBar();

        // Recyclerview setzen.
        mAdapter = new CustomAdapter();
        // Setze Liste mit den Testposts
        mAdapter.mPostList = Testdata.createPostList();


        mRecyclerView = (RecyclerView) findViewById( R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager( this ));
        Log.d(TAG, "3");
        mRecyclerView.setAdapter( mAdapter );

    }

    // Lifecycle-Funktion: onStart

    @Override
    protected void onStart() {
        super.onStart();

        // If there is no current user, jump to sign in...
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplication(), SignInActivity.class);
            startActivity(intent);
        } else {

        }
        Log.d(TAG, "onStart called.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        String msg;
        switch( item.getItemId()){
            case R.id.menu_testAuthStatus:
                doTestAuthState();
                return true;

            case R.id.menu_createTestuser:
                doCreateUser();
                return true;

            case R.id.menu_signInTestuser:
                doSignIn();
                return true;

            case R.id.menu_signOutTestuser:
                doSignOut();
                return true;

            case R.id.menu_deleteTestuser:
                doDeleteUser();
                return true;

            case R.id.menu_sendResetPasswordMail:
                doResetPassword();
                return true;

            case R.id.menu_sendActivationMail:
                doSendActivationMail();
                return true;

            case R.id.menu_post:
                intent = new Intent(getApplication(), PostActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_manage_account:
                 intent = new Intent(getApplication(), ManageAccountActivity.class);
                startActivity(intent);
                return true;

        }
        return true;
    }

    private void doSendActivationMail() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            msg = "You must be signed in request a verification mail";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }

        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg;
                if (task.isSuccessful()){
                    msg = "Mail sent.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else {
                    msg = "Sending mail failed: " + task.getException();
                    Log.d(TAG, msg);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void doResetPassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail( Testdata.TEST_EMAIL)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg;
                        if (task.isSuccessful()) {
                            msg = "E-Mail sent.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        } else {
                            msg = "Sending mail failed: " + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            Log.d(TAG, msg);
                        }
                    }
                });
    }

    private void doDeleteUser() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            msg = "You must be signed in to delete the account";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }

        user.delete().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg;
                if (task.isSuccessful()){
                    msg = "Account deleted.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else {
                    msg = "Deletion failed: " + task.getException();
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
        }
        else {
            msg = "Sorry, no current user.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    private void doSignIn() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            msg = "User signed in, please sign out first.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword( Testdata.TEST_EMAIL, Testdata.TEST_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg;
                        if (task.isSuccessful()){
                            msg = "Your are signed in.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                        else {
                            msg = "Sign in failed: " + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void doCreateUser() {
        showProgressBar();
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword( Testdata.TEST_EMAIL, Testdata.TEST_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg;
                        if (task.isSuccessful()){
                            msg = "User created";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            hideProgressBar();
                        }
                        else {
                            msg = "User creation failed:" + task.getException();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            hideProgressBar();
                        }
                    }
                });
    }

    private void doTestAuthState() {
        String msg;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            msg = "User : " + user.getEmail()
                    + (user.isEmailVerified() ? " (verified)" : " (not verified)") ;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
        else {
            msg = "No user signed in";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called.");
    }

    public void showProgressBar(){
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar(){
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}
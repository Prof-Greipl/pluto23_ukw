package de.hawlandshut.pluto23_ukw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "xx MainActivitity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called.");
        Toast.makeText(getApplicationContext(), "You pressed Test.", Toast.LENGTH_LONG).show();
    }

    // Lifecycle-Funktion: onStart

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplication(), CreateAccountActivity.class);
        startActivity(intent);
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
        switch( item.getItemId()){
            case R.id.menu_post:
                Toast.makeText(getApplicationContext(), "You pressed Post.", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_manage_account:
                Toast.makeText(getApplicationContext(), "You pressed Manage Account.", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_test:
                Toast.makeText(getApplicationContext(), "You pressed Test.", Toast.LENGTH_LONG).show();
                return true;
        }
        return true;
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
}
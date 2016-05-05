package com.example.wojtek_asus.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    Firebase myFirebaseRef = new Firebase("https:fiery-torch-1348.firebaseio.firebaseio.com/");
    TextView password_tv;
    TextView username_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username_tv = (TextView) findViewById(R.id.InputLogin);
        password_tv = (TextView) findViewById(R.id.InputHaslo);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Login(View view)
    {
        User.getInstance().username = username_tv.getText().toString();
        User.getInstance().password = password_tv.getText().toString();
        Intent intent = new Intent(this, ContactsList.class);
        startActivity(intent);

    }
}

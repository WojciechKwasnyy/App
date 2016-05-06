package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User.getInstance().myFirebaseRef.createUser("Adadadad", "cccc", new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                AlertDialog.Builder builder11 = new AlertDialog.Builder(getApplicationContext());
                builder11.setMessage("Dodano Użytkownika").show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                AlertDialog.Builder builder11 = new AlertDialog.Builder(getApplicationContext());
                builder11.setMessage("Nie dodano Użytkownika").show();
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


}

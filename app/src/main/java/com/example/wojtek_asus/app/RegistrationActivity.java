package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    public int sum;
    EditText loginn;
    AlertDialog builder11;

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    public void SignUp(View view) throws IOException {
        try {


            Firebase myFirebaseRef = new Firebase("https://fiery-torch-1348.firebaseio.com");
            Firebase.setAndroidContext(getApplicationContext());
            final EditText login = (EditText) findViewById(R.id.LoginRejestration);
            loginn = login;
            EditText password = (EditText) findViewById(R.id.PasswordRejestration);
            loginClicked();
            passwordClicked();
            myFirebaseRef.createUser(login.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {

                    AlertDialog.Builder builder119 = new AlertDialog.Builder(getApplicationContext());
                    builder119.setMessage("Dodano Użytkownika" + login.getText().toString());
                    ;
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    AlertDialog.Builder builder119 = new AlertDialog.Builder(getApplicationContext());
                    builder119.setMessage(firebaseError.toString() + "Użytkownik już istnieje");
                }
            });


        } catch (Exception ex) {
            AlertDialog.Builder builder113 = new AlertDialog.Builder(getApplicationContext());
            builder113.setMessage(ex.getMessage()).show();
        }
        if (sum < 1) {
            AlertDialog.Builder builder119 = new AlertDialog.Builder(this);
            builder119.setMessage("Dodano Użytkownika" + loginn.getText().toString());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        builder11 = new AlertDialog.Builder( RegistrationActivity.this).create();
        builder11.setTitle("Zarejestruj użytkownika");
        builder11.setMessage(" \n \n • Podaj login w postaci maila \n \u2022 Podaj hasło zawierające: \n\t\t - Minimum dwie małe litery \n" +
                "\t\t - Minimum Dwie duże litery \n \t\t - Minimum Dwie cyfry \n \t\t - Minimum 8 znaków");

        builder11.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                builder11.cancel();
            }
        });
        builder11.setCancelable(false);
        builder11.show();


        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inte = new Intent(this, MainActivity.class);
        startActivity(inte);
    }

    private void loginClicked() {
        EditText Logintmp = (EditText) findViewById(R.id.LoginRejestration);
        String userName = Logintmp.getText().toString();
        if (userName.isEmpty()) {
            sum++;
            AlertDialog.Builder builder13 = new AlertDialog.Builder(this);
            builder13.setMessage("Wprowadź Login").show();
        }


    }

    private void passwordClicked() {
        EditText Passwordtmp = (EditText) findViewById(R.id.PasswordRejestration);
        String UserPass = Passwordtmp.getText().toString();
        if (UserPass.length() < 8) {
            sum++;

            AlertDialog.Builder builder14 = new AlertDialog.Builder(this);
            builder14.setMessage("Za krótkie hasło").show();
        }
        int countnum = 0;
        int countupp = 0;
        int countlow = 0;

        for (int i = 0; i < UserPass.length(); i++) {
            if (Character.isDigit(UserPass.charAt(i))) {
                countnum++;
            }
        }

        for (int i = 0; i < UserPass.length(); i++) {
            if (Character.isUpperCase(UserPass.charAt(i))) {
                countupp++;
            }
        }
        for (int i = 0; i < UserPass.length(); i++) {
            if (Character.isLowerCase(UserPass.charAt(i))) {
                countlow++;
            }
        }
        if (countnum < 2) {
            sum++;
            AlertDialog.Builder builder15 = new AlertDialog.Builder(this);
            builder15.setMessage("Hasło powinno zawierać minimum 2 cyfry").show();
        }
        if (countlow < 2) {
            sum++;
            AlertDialog.Builder builder17 = new AlertDialog.Builder(this);
            builder17.setMessage("Hasło powinno zawierać minimum 2 małe litery").show();
        }
        if (countupp < 2) {
            sum++;
            AlertDialog.Builder builder16 = new AlertDialog.Builder(this);
            builder16.setMessage("Hasło powinno zawierać minimum 2 duże litery").show();
        }
    }

    class TempUser {
        String TmpLogin;
        String TmpPass;

        TempUser(String TmpLogin, String TmpPass) {
            this.TmpLogin = TmpLogin;
            this.TmpPass = TmpPass;
        }


    }
}
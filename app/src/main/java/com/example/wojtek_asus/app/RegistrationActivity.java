package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {



    public void SignUp(View view) throws IOException
    {
try
{


    Firebase myFirebaseRef = new Firebase("https://fiery-torch-1348.firebaseio.com");
    Firebase.setAndroidContext(getApplicationContext());
        EditText login = (EditText) findViewById(R.id.LoginRejestration);
        EditText password = (EditText) findViewById(R.id.PasswordRejestration);
        Button SignUp = (Button)findViewById(R.id.btRejestration);

    Firebase usersRef = myFirebaseRef.child("Users");
    Map<String, String> UserToAdd = new HashMap<String, String>();
    UserToAdd.put("Password", password.getText().toString());
    UserToAdd.put("Login", login.getText().toString());
   // Map<Boolean, Map<String, String>> UserToAdd2 = new HashMap<Boolean, Map<String, String>>();

    //UserToAdd2.put(false, UserToAdd);

    usersRef.push().setValue(UserToAdd);
    //Firebase Userek = myFirebaseRef.child("Sebix").child("2134");
   // TempUser Sebix = new TempUser("Sebix", "1234");
    //Userek.setValue(Sebix);

       /* myFirebaseRef.createUser(login.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
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
*/
        }
        catch(Exception ex)
        {
            AlertDialog.Builder builder113 = new AlertDialog.Builder(getApplicationContext());
            builder113.setMessage(ex.getMessage()).show();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

class TempUser
{
    String TmpLogin;
    String TmpPass;

    TempUser(String TmpLogin, String TmpPass)
    {
        this.TmpLogin=TmpLogin;
        this.TmpPass=TmpPass;
    }



}}
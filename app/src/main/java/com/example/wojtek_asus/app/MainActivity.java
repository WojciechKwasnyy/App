package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    TextView password_tv;
    TextView username_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

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
        try{
            File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName() + "/Configuration");
            if (!file.exists())
                file.mkdirs();
            file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName() + "/Configuration/config_"+User.getInstance().username+".txt");
            if(!file.exists()) {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file, false);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(User.getInstance().username);
                os.close();
                fos.close();
            }
        }catch(Exception e){
            AlertDialog.Builder builder11 = new AlertDialog.Builder(this);
            builder11.setMessage(e.getMessage()).show();
        }
        loginClicked();


    }
    public void Register(View view)
    {
        AlertDialog.Builder builder11 = new AlertDialog.Builder(this);
        builder11.setMessage("Zarejestruj użytkownika").show();
        Intent intent = new Intent (this, RegistrationActivity.class);
        startActivity(intent);

    }
    private void loginClicked() {
        EditText Logintmp = (EditText) findViewById(R.id.InputLogin);
        String userName = Logintmp.getText().toString();
        if (userName.isEmpty()) {

            AlertDialog.Builder builder12 = new AlertDialog.Builder(this);
            builder12.setMessage("Wprowadź Login").show();

        }
        else
        {
            Intent intent = new Intent(this, ContactsList.class);
            startActivity(intent);
        }

    }
}

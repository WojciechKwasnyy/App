package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {

    TextView password_tv;
    TextView username_tv;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {@Override public void onAdClosed(){requestNewInterstitial();further();}});
        requestNewInterstitial();

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

    private void requestNewInterstitial()
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    public void Firsts(View view)
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
        else
        {
            Login (view);
        }



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
        Intent intent = new Intent(this, ContactsList.class);
        startActivity(intent);

    }

    public void further()
    {
        AlertDialog.Builder builder11 = new AlertDialog.Builder(this);
        builder11.setMessage("Dobry").show();
        Intent intent = new Intent (this,ContactsList.class);
        startActivity(intent);
    }
    public void Register(View view)
    {
        AlertDialog.Builder builder11 = new AlertDialog.Builder(this);
        builder11.setMessage("Napad").show();
        Intent intent = new Intent (this,RegistrationActivity.class);
        startActivity(intent);

    }

}

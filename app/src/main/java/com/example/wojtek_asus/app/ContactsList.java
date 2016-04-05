package com.example.wojtek_asus.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messagingtutorialskeleton.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
public class ContactsList extends AppCompatActivity {

    private ListView contactslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contactslist = (ListView) findViewById(R.id.contactslistview);
        ArrayList<String> StringArray = new ArrayList<String>();
        StringArray.add("Smth");
        List<String> contactlist = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            contactlist.add("Janek" + Integer.toString(i));
        }

        Button btcall = (Button) findViewById(R.id.btcall);
        btcall.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                //SinchClient sinchClient = Sinch.get
            }
        });
    }



       // SimpleAdapter adapter = new SimpleAdapter(this, );




}


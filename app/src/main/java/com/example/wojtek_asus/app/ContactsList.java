package com.example.wojtek_asus.app;

import android.content.Context;
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

//import com.example.messagingtutorialskeleton.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
public class ContactsList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("wojtekkwa@o2.pl")
                .applicationKey("3cc0c725-63fb-4410-a505-c438eeea1041")
                .applicationSecret("2V4L1bcagE+VcapWvc8gig==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.start();

        Button btcall = (Button) findViewById(R.id.btcall);
        btcall.setOnClickListener(new View.OnClickListener(){

            @Override
        public void onClick(View vie3w){

                sinchClient.getCallClient().callUser("call-recipient-id");
            }
        });
    }



       // SimpleAdapter adapter = new SimpleAdapter(this, );




}


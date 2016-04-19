package com.example.wojtek_asus.app;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

/**
 * Created by Białyy on 2016-04-16.
 */
public class CallingService extends IntentService {
    Handler mHandler = new Handler();
    public CallingService() {
        super("CallingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Siemano tu serwis!", Toast.LENGTH_SHORT).show();


                User.getInstance().sinchClient = Sinch.getSinchClientBuilder()
                        .context(getApplicationContext())
                        .userId(User.getInstance().username)
                                //.userId("wojtekkwa@o2.pl")
                        .applicationKey("3cc0c725-63fb-4410-a505-c438eeea1041")
                        .applicationSecret("2V4L1bcagE+VcapWvc8gig==")
                        .environmentHost("sandbox.sinch.com")
                        .build();
                User.getInstance().sinchClient.setSupportCalling(true);

                //Rozpoczęcie nasłuchiwania połączeń przychodzących
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // Code here will run in UI thread
                        User.getInstance().sinchClient.start();
                        User.getInstance().sinchClient.startListeningOnActiveConnection();
                        User.getInstance().sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                    }
                });


            }
        });


    }


    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            User.getInstance().call = incomingCall;
            Toast.makeText(getApplicationContext(), "DZWONIO!!!!!!",
                    Toast.LENGTH_LONG).show();
            User.getInstance().call.answer();
            Intent intent = new Intent(getApplicationContext(),CallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
           // btcall.setText("Hang Up");

        }
    }


}

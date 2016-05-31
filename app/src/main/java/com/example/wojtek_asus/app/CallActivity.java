package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.sql.Time;
import java.util.List;

public class CallActivity extends AppCompatActivity {
    AudioManager audioManager ;
    public Chronometer chrono = (Chronometer) findViewById(R.id.chronometer1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView zkimgadam = (TextView) findViewById(R.id.textView2);
        zkimgadam.setText(User.getInstance().call.getRemoteUserId());
        User.getInstance().call.addCallListener(new SinchCallListener());
        chrono.start();


    }
void VIDEO (View view)
{
    Intent intent = new Intent(this, VideoConversation.class);
    startActivity(intent);
}

    public class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            User.getInstance().call = null;
            //chrono.stop();
            //Time time = (Time) chrono.getOnChronometerTickListener();
            //AlertDialog.Builder builder1119 = new AlertDialog.Builder(getApplicationContext());
            //builder1119.setMessage("Użytkownik już istnieje" + time);


            Intent intent = new Intent(getApplicationContext(),ContactsList.class);
            //audioManager.setSpeakerphoneOn(false);
            startActivity(intent);
        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            //setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
             Intent intent = new Intent(getApplicationContext(), CallActivity.class);
            startActivity(intent);
            //incoming CallActivity was picked up

            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(false);


        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            //CallActivity is ringing
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
    }

    public void endCall(View view)
    {
        User.getInstance().call.hangup();
    }
    
}

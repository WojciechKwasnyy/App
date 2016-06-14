package com.example.wojtek_asus.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.video.VideoController;

import java.util.List;

public class CallActivity extends BaseActivity  {
    AudioManager audioManager ;
    VideoController vc;
    CallEndCause caus;
    boolean videoinviter = false;
    AudioPlayer mAudioPlayer;
    private String mCallId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView zkimgadam = (TextView) findViewById(R.id.textView2);
        zkimgadam.setText(User.getInstance().call.getRemoteUserId());
        User.getInstance().call.addCallListener(new SinchCallListener());
if(User.getInstance().call.getDetails().isVideoOffered() == true)
{
    onVideoTrackAdded();

}
    }


    public class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            //if(User.getInstance().call.getDetails().isVideoOffered() == true) {
            //recreate();
            //}

            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            User.getInstance().call = endedCall;

            if(videoinviter)
            {
                videoinviter = false;
                //Intent intent = new Intent(getApplicationContext(),CallActivity.class);
                //audioManager.setSpeakerphoneOn(false);
              // startActivity(intent);
            }
            else {

                //'finish();
                Intent intent = new Intent(getApplicationContext(), ContactsList.class);
                //audioManager.setSpeakerphoneOn(false);
                startActivity(intent);
            }
        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            //setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            // Intent intent = new Intent(getApplicationContext(), CallActivity.class);
            //startActivity(intent);
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

    public void endCall(View view) {
        //mAudioPlayer.stopProgressTone();

        if (User.getInstance().call != null) {
            User.getInstance().call.hangup();
                    }
finish();
    }
    public void onVideoTrackAdded()
    {
vc = User.getInstance().sinchClient.getVideoController();

        LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
        view.addView(vc.getRemoteView());
        RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
        localView.addView(vc.getLocalView());
        //View myPreview = vc.getLocalView();
        //View remoteView = vc.getRemoteView();
        User.getInstance().sinchClient.getAudioController();

    }

    public void onCallEnded(Call call) {
        CallEndCause cause = call.getDetails().getEndCause();

        mAudioPlayer.stopProgressTone();
        setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);


        //endCall(View view);
    }
    public void VideoCallButtonClicked(View view) {
        User.getInstance().call.hangup();
        videoinviter = true;
       onVideoTrackAdded();

        String userName = User.getInstance().call.getRemoteUserId();
        if (userName.isEmpty()) {
            Toast.makeText(this, "Nie przeczytałem odbiorcy", Toast.LENGTH_LONG).show();
            return;
        }
        User.getInstance().call = User.getInstance().sinchClient.getCallClient().callUserVideo(userName);

        User.getInstance().call.getCallId();



    }
}

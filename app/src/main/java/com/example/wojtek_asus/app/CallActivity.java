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

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


    public class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            User.getInstance().call = endedCall;
            Intent intent = new Intent(getApplicationContext(),ContactsList.class);
            //audioManager.setSpeakerphoneOn(false);

            startActivity(intent);
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

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }
    public void onVideoTrackAdded(Call call)
    {
        vc = getSinchServiceInterface().getVideoController();
        LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
        view.addView(vc.getRemoteView());
        RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
        localView.addView(vc.getLocalView());
        View myPreview = vc.getLocalView();
        View remoteView = vc.getRemoteView();
    }

    public void onCallEnded(Call call) {
        CallEndCause cause = call.getDetails().getEndCause();

        mAudioPlayer.stopProgressTone();
        setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        String endMsg = "Call ended: ";

        endCall();
    }
    private void VideoCallButtonClicked(View view) {
       onVideoTrackAdded(User.getInstance().call);
        String userName = User.getInstance().call.getRemoteUserId();
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }
        Call call = getSinchServiceInterface().callUserVideo(userName);
        String callId = call.getCallId();
call.answer();
    }
}

package com.example.wojtek_asus.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.video.VideoController;


public class VideoConversation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AudioPlayer mAudioPlayer;



        mAudioPlayer = new AudioPlayer(this);
        TextView CallerName = (TextView) findViewById(R.id.CallerName);
        CallerName.setText(User.getInstance().call.getRemoteUserId());

        Button endCallButton = (Button) findViewById(R.id.btEndVideo);

        mAudioPlayer.stopProgressTone();
        User.getInstance().call.hangup();
        finish();

    }/*
    @Override
    public void onVideoTrackAdded(Call call) {
        // Get a reference to your SinchClient, in the samples this is done through the service interface:
        VideoController vc = getSinchServiceInterface().getVideoController();
       View myPreview = vc.getLocalView();
        View remoteView = vc.getRemoteView();

    }
*/


}

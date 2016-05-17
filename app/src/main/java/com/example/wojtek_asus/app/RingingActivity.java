package com.example.wojtek_asus.app;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RingingActivity extends AppCompatActivity {

MediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.ring);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(1f, 1f);
        mMediaPlayer.start();

    }
    public void receivecall(View view){
        mMediaPlayer.stop();
        User.getInstance().call.answer();
        Intent intent = new Intent(this, CallActivity.class);
        startActivity(intent);

    }
    public void abortcall(View view){
        mMediaPlayer.stop();
        User.getInstance().call.hangup();
        Intent intent = new Intent(this, ContactsList.class);

        startActivity(intent);

    }

}

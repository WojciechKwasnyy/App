package com.example.wojtek_asus.app;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Created by Białyy on 2016-05-10.
 */
public class RingingService extends IntentService {

    MediaPlayer mMediaPlayer;
    public RingingService() {
        super("RingingService");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }
}

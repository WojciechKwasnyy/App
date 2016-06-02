package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
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
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Białyy on 2016-04-16.
 */
public class CallingService extends Service {
    Handler mHandler = new Handler();
    private String username;
    private List<ChatMessage> messages;
    @Override
    public void onCreate() {
         username = null;

        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        final MessagesSaver messagesSaver = new MessagesSaver();

        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    /*
                    FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Configuration/config_" + User.getInstance().username + ".txt");
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Object readObject = is.readObject();
                    is.close();
                    username = (String) readObject;*/
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {



                /*try {
                    FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Configuration/config_" + User.getInstance().username + ".txt");
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Object readObject = is.readObject();
                    is.close();
                    username = (String) readObject;

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }*/
                           // User.getInstance().username = username;
                            Toast.makeText(getApplicationContext(), "Siemano tu serwis!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "przeczytalem usera " + User.getInstance().username, Toast.LENGTH_SHORT).show();

                            User.getInstance().sinchClient = Sinch.getSinchClientBuilder()
                                    .context(getApplicationContext())
                                            //.userId(username)
                                    .userId(User.getInstance().username)
                                    .applicationKey("3cc0c725-63fb-4410-a505-c438eeea1041")
                                    .applicationSecret("2V4L1bcagE+VcapWvc8gig==")
                                    .environmentHost("sandbox.sinch.com")
                                    .build();
                            User.getInstance().sinchClient.setSupportCalling(true);
                            User.getInstance().sinchClient.setSupportMessaging(true);
                            //Rozpoczęcie nasłuchiwania połączeń przychodzących
                            messages = messagesSaver.readmessages(getApplicationContext());

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    // Code here will run in UI thread
                                    User.getInstance().sinchClient.start();
                                    User.getInstance().sinchClient.startListeningOnActiveConnection();
                                    User.getInstance().sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                    final String hour = simpleDateFormat.format(calendar.getTime());
                                    MessageClient messageClient = User.getInstance().sinchClient.getMessageClient();
                                    messageClient.addMessageClientListener(new MessageClientListener() {
                                        @Override
                                        public void onIncomingMessage(MessageClient messageClient, Message message) {
                                            messagesSaver.savemessage((new ChatMessage(false, message.getTextBody(), message.getSenderId(), hour)), messages,getApplicationContext());
                                        }

                                        @Override
                                        public void onMessageSent(MessageClient messageClient, Message message, String s) {

                                        }

                                        @Override
                                        public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {

                                        }

                                        @Override
                                        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {

                                        }

                                        @Override
                                        public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {

                                        }
                                    });
                                }
                            });

                        }
                    });

                } finally {

                }
            }
        };
        t.start();



        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Zawijam, ELO",
                Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    public class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            User.getInstance().call = incomingCall;
            Toast.makeText(getApplicationContext(), "DZWONIO!!!!!!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),RingingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
           // User.getInstance().call.answer();

           // btcall.setText("Hang Up");

        }
		/*public VideoController getVideoController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getVideoController();
        }
        */
    }


}

        package com.example.wojtek_asus.app;

        import android.app.AlertDialog;
        import android.app.IntentService;
        import android.app.Service;
        import android.content.Intent;
        import android.media.AudioManager;
        import android.media.Ringtone;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.IBinder;
        import android.support.annotation.Nullable;
        import android.support.v4.content.LocalBroadcastManager;
        import android.widget.Toast;

        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.sinch.android.rtc.ClientRegistration;
        import com.sinch.android.rtc.PushPair;
        import com.sinch.android.rtc.Sinch;
        import com.sinch.android.rtc.SinchClient;
        import com.sinch.android.rtc.SinchClientListener;
        import com.sinch.android.rtc.SinchError;
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
    String usr;
    private List<ChatMessage> messages;
    LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        username = null;
        User.getInstance().sinchClient = null;
        User.getInstance().messageClient = null;
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        final MessagesSaver messagesSaver = new MessagesSaver();

        broadcaster = LocalBroadcastManager.getInstance(this);




        final Thread t = new Thread() {
            @Override
            public void run() {
                try {

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            // User.getInstance().username = username;
                            Toast.makeText(getApplicationContext(), "Serwis gotowy", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Zalogowano Użytkownika  " + User.getInstance().username, Toast.LENGTH_SHORT).show();

                            User.getInstance().sinchClient = Sinch.getSinchClientBuilder()
                                    .context(getApplicationContext())
                                            //.userId(username)
                                    .userId(User.getInstance().username)
                                    .applicationKey("3cc0c725-63fb-4410-a505-c438eeea1041")
                                    .applicationSecret("2V4L1bcagE+VcapWvc8gig==")
                                    .environmentHost("sandbox.sinch.com")
                                    .build();

                            //Rozpoczęcie nasłuchiwania połączeń przychodzących
                            messages = messagesSaver.readmessages(getApplicationContext());



                            if(!User.getInstance().sinchClient.isStarted()) {
                                try{

                                    User.getInstance().sinchClient.setSupportMessaging(true);
                                    User.getInstance().sinchClient.setSupportCalling(true);
                                User.getInstance().sinchClient.start();

                                    User.getInstance().sinchClient.addSinchClientListener(new SinchClientListener() {
                                        public void onClientStarted(SinchClient client) {
                                            User.getInstance().sinchClient.startListeningOnActiveConnection();}
                                        public void onClientStopped(SinchClient client) {
                                            User.getInstance().sinchClient = null;
                                        }
                                        public void onClientFailed(SinchClient client, SinchError error) {
                                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();}
                                        public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration registrationCallback) { }
                                        public void onLogMessage(int level, String area, String message) { }
                                    });

                                User.getInstance().sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                final String hour = simpleDateFormat.format(calendar.getTime());
                                    User.getInstance().messageClient = User.getInstance().sinchClient.getMessageClient();
                                User.getInstance().messageClient.addMessageClientListener(new MessageClientListener() {
                                    @Override
                                    public void onIncomingMessage(MessageClient messageClient, Message message) {
                                        messages = messagesSaver.readmessages(getApplicationContext());
                                        messagesSaver.savemessage((new ChatMessage(false, message.getTextBody(), message.getSenderId(), hour, User.getInstance().username)), messages, getApplicationContext());
                                        User.getInstance().messages = messagesSaver.readmessages(getApplicationContext());

                                        usr = message.getSenderId();
                                        Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

                                        Runnable myRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "Wiadomość od " + usr, Toast.LENGTH_LONG).show();
                                                try {
                                                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                                    r.play();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } // This is your code
                                        };
                                        mainHandler.post(myRunnable);

                                        Toast.makeText(getApplicationContext(), "Wiadomość od " + message.getSenderId(), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent("messenger");
                                        intent.putExtra("message", message.getTextBody());
                                        intent.putExtra("messagewho", message.getSenderId());
                                        broadcaster.sendBroadcast(intent);
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
                                });}catch (Exception e){Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}
                            }





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

            // User.getInstance().call.answer();

            // btcall.setText("Hang Up");
if(incomingCall.getDetails().isVideoOffered()== true)
{
    User.getInstance().call = incomingCall;
    User.getInstance().call.answer();
    Toast.makeText(getApplicationContext(), "Videło",
            Toast.LENGTH_LONG).show();
    Intent inte = new Intent(getApplicationContext(),CallActivity.class);
    inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(inte);
}
            else
{
    User.getInstance().call = incomingCall;
    Toast.makeText(getApplicationContext(), "Nie wideło",
            Toast.LENGTH_LONG).show();
    Intent intent = new Intent(getApplicationContext(),RingingActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
}
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

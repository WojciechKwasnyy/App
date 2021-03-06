package com.example.wojtek_asus.app;

import com.firebase.client.Firebase;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.messaging.MessageClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Białyy on 2016-04-13.
 */
public class User implements Serializable{
    public static User instance = new User();
    public String username;
    public String password;

    public MessageClient messageClient= null;
    public transient Call call;
    public transient SinchClient sinchClient = null;
    public List<ChatMessage> messages;


    protected User() {
        // Exists only to defeat instantiation.
    }
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }
}

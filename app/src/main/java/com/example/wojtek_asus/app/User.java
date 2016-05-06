package com.example.wojtek_asus.app;

import com.firebase.client.Firebase;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;

import java.io.Serializable;

/**
 * Created by Białyy on 2016-04-13.
 */
public class User implements Serializable{
    public static User instance = new User();
    public String username;
    public String password;
    Firebase myFirebaseRef = new Firebase("https://fiery-torch-1348.firebaseio.com");
    public transient Call call;
    public transient SinchClient sinchClient;
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

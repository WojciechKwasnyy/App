package com.example.wojtek_asus.app;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;

/**
 * Created by Białyy on 2016-04-13.
 */
public class User {
    private static User instance = null;
    public String username;
    public String password;
    public Call call;
    public SinchClient sinchClient;
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

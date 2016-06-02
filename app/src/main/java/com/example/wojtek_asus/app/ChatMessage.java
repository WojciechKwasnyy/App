package com.example.wojtek_asus.app;

import java.io.Serializable;

/**
 * Created by Wojtek-ASUS on 02.06.2016.
 */
public class ChatMessage implements Serializable {
    public boolean left;
    public String message;
    public String user;
    public String date;

    public ChatMessage(boolean left, String message, String user, String date) {
        super();
        this.left = left;
        this.message = message;
        this.user = user;
        this.date = date;
    }
}

package com.example.wojtek_asus.app;

import java.io.Serializable;

/**
 * Created by Wojtek-ASUS on 02.06.2016.
 */
public class ChatMessage implements Serializable {
    public boolean left;
    public String message;
    public String sender;
    public String date;
    public String recipient;

    public ChatMessage(boolean left, String message, String sender, String date,String recipient) {
        super();
        this.left = left;
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        this.date = date;
    }
}

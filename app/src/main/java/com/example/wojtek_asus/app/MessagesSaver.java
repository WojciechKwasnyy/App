package com.example.wojtek_asus.app;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojtek-ASUS on 02.06.2016.
 */
public class MessagesSaver {
    public void savemessage(ChatMessage chatMessage,List<ChatMessage> messages, Context context) {
        try {
            if(messages.size()>50)
                messages.remove(0);
            messages.add(chatMessage);
            File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/Chat");
            if (!file.exists())
                file.mkdirs();
            file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/Chat/msgs_"+User.getInstance().username);
            FileOutputStream fos = new FileOutputStream(file,false);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(messages);
            os.close();
            fos.close();
        }catch(Exception e){
            AlertDialog.Builder builder11 = new AlertDialog.Builder(context);
            builder11.setMessage(e.getMessage()).show();
        }
    }
    public List<ChatMessage> readmessages(Context context)
    {
        List <ChatMessage> messages = new ArrayList<ChatMessage>();
        try{
            File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/Chat");
            if (!file.exists())
                file.mkdirs();
            file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/Chat/msgs_"+User.getInstance().username);
            if(!file.exists()) {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file,false);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(messages);
                os.close();
                fos.close();
            }
            FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/Chat/msgs_"+User.getInstance().username);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object readObject = is.readObject();
            is.close();
            messages = (List <ChatMessage>) readObject;

        }catch(Exception e){
            AlertDialog.Builder builder11 = new AlertDialog.Builder(context);
            builder11.setMessage(e.getMessage()).show();
        }
        return messages;

    }
}

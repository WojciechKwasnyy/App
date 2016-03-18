package com.example.wojtek_asus.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.widget.SimpleAdapter;

public class ContactsList extends AppCompatActivity {

    private ListView contactslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contactslist = (ListView) findViewById(R.id.contactslistview);
        ArrayList<String> StringArray = new ArrayList<String>();
        StringArray.add("Smth");
        List<String> contactlist = new ArrayList<String>();
        for (int i = 0; i<10; i++)
        {
            contactlist.add("Janek" + Integer.toString(i));
        }
        }
       // SimpleAdapter adapter = new SimpleAdapter(this, );




}

package com.example.wojtek_asus.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String chosenuser =  (String) getIntent().getSerializableExtra("Chosen");
        getSupportActionBar().setTitle(chosenuser);

       

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.call, menu);  ------ DO ODKOMENTOWANIA
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_call) ----- DO ODKOMENTOWANIA

        {
            try {
               // User.getInstance().call = User.getInstance().sinchClient.getCallClient().callUser("call-recipient-id");
                Intent intent = new Intent(getApplicationContext(), CallActivity.class);
                startActivity(intent);
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Nie da rady :/" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
            return true;
        }

        //return super.onOptionsItemSelected(item);
    }

}

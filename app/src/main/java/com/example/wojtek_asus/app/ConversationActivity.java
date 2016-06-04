package com.example.wojtek_asus.app;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sinch.android.rtc.messaging.WritableMessage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {
    String chosenuser;
    private EditText chatText;
    private Button buttonSend;
    private ListView listView;
    private boolean side =true;
    private ChatArrayAdapter chatArrayAdapter;
    private List<ChatMessage> messages;
    private MessagesSaver messagesSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chosenuser = (String) getIntent().getSerializableExtra("Chosen");
        //getIntent().getStringExtra("Chosen");
        getSupportActionBar().setTitle(chosenuser);

        buttonSend = (Button) findViewById(R.id.send_button);
        listView = (ListView) findViewById(R.id.messages_view);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);
        chatText = (EditText) findViewById(R.id.message_input);
        messagesSaver = new MessagesSaver();
        messages = new ArrayList<ChatMessage>();
        messages = messagesSaver.readmessages(this);
        for(int i =0; i<messages.size();i++)
        {
            chatArrayAdapter.add(messages.get(i));
        }
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        return sendChatMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

        });


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try {
                    sendChatMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.call, menu);
        return true;
    }

   public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call) {
            try {
                User.getInstance().call = User.getInstance().sinchClient.getCallClient().callUser(chosenuser);
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

        return super.onOptionsItemSelected(item);
    }

    private boolean sendChatMessage()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        chatArrayAdapter.add(new ChatMessage(true, chatText.getText().toString(), User.getInstance().username, formattedDate));
        messagesSaver.savemessage((new ChatMessage(true, chatText.getText().toString(), User.getInstance().username, formattedDate)), messages, ConversationActivity.this);
        WritableMessage msg = new WritableMessage(chosenuser, chatText.getText().toString());
        User.getInstance().messageClient.send(msg);
        chatText.setText("");
        return true;
    }
}




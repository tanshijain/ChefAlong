package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    Button send;
    EditText messageField;
    TextView textView;
    FirebaseDatabase db;
    String topic;
    MessageAdapter messageAdapter;
    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        backButton = findViewById(R.id.backButton);
        topic = getIntent().getExtras().getString("topic");
        send = findViewById(R.id.sendButton);
        messageField = findViewById(R.id.MessageText);
        //textView = findViewById(R.id.textView);
        ListView messageView = findViewById(R.id.messageListView);
        ArrayList <MessageData> messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(MessageActivity.this, messages);

        messageView.setAdapter(messageAdapter);

        messageView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(MessageActivity.this, MessageGroupListActivity.class);
                startActivity(i);
                finish();
            }
        });

        db = FirebaseDatabase.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textMessage = messageField.getText().toString();
                if (textMessage.length() == 0) {
                    return;
                }
                Map<String, String> m = new HashMap<>();
                m.put("message text", textMessage);
                m.put("user", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                db.getReference("messages/"+topic).push().setValue(m);
            }

        });

        db.getReference("messages/"+topic).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> data = (Map) dataSnapshot.getValue();
                //create a message

                //textView.append(data.get("user") + ": " + data.get("message text") +"\n");
                messageAdapter.add(new MessageData(data.get("message text"), data.get("user")));
                Log.d("message", data.get("message text"));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}




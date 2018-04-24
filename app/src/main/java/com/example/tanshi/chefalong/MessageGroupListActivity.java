package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class MessageGroupListActivity extends AppCompatActivity {
    ArrayList<MessageGroupListItemInfo> topics = new ArrayList();
    MessageGroupListAdapter adapter;
    Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group_list);

        ListView topicListView = findViewById(R.id.TopicListView);
        adapter = new MessageGroupListAdapter(this, topics);
        topicListView.setAdapter(adapter);
        homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(MessageGroupListActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        FirebaseDatabase.getInstance().getReference("topicList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> data = (Map) dataSnapshot.getValue();
                adapter.add(new MessageGroupListItemInfo(data.get("topic"), data.get("description")));
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


        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(MessageGroupListActivity.this, MessageActivity.class);
                i.putExtra("topic", adapter.getItem(position).topic);
                startActivity(i);

            }
        });
    }
}




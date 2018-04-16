package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameView = findViewById(R.id.textView1);
        final TextView addressView = findViewById(R.id.textView2);
        Button hostButton = findViewById(R.id.hostButton);
        Button addButton = findViewById(R.id.addButton);
        Button mapButton = findViewById(R.id.mapButton);
        Button messageButton = findViewById(R.id.messageButton);

        nameView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        //accessing extra info (address) from database to display on profile

        db.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);
                addressView.setText(userInfo.address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(ProfileActivity.this, HostActivity.class);
                startActivity(i);
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(ProfileActivity.this, MessageGroupListActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}

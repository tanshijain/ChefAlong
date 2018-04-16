package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostActivity extends AppCompatActivity {
    String topicText;
    String descriptionText;
    EditText dishText;
    EditText cuisineText;
    EditText numGuestsText;
    EditText timeText;
    EditText durationText;
    UserInfo userInfo;
    String address;
    FirebaseDatabase db;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    HashMap<String,String> emptyAttendees;

    private void addNewDishInfo(String userId, String dish, String cuisine, int numGuests, String timeText, int duration, HashMap<String, String> attendees, double latitude, double longitude) {
        DishInfo event = new DishInfo(dish, cuisine, numGuests, timeText, duration, attendees, latitude, longitude);
        ref.child("events").push().setValue(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        dishText = findViewById(R.id.dishField);
        cuisineText = findViewById(R.id.cuisineField);
        numGuestsText = findViewById(R.id.numGuestsField);
        timeText = findViewById(R.id.timeField);
        durationText = findViewById(R.id.durationField);
        Button addButton = findViewById(R.id.addButton);
        db = FirebaseDatabase.getInstance();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicText = dishText.getText().toString();
                descriptionText = numGuestsText.getText().toString();
                if ((topicText.length() == 0) || (descriptionText.length() == 0)) {
                    return;

                }
                ref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userInfo = dataSnapshot.getValue(UserInfo.class);
                        address = userInfo.address;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //convert address to latitude and longitude with geocoding-- this is a placeholder:
                double latitude = 190.2;
                double longitude = 23.3;


                addNewDishInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(), dishText.getText().toString(), cuisineText.getText().toString(), Integer.parseInt(numGuestsText.getText().toString()), timeText.getText().toString(), Integer.parseInt(durationText.getText().toString()), emptyAttendees, latitude, longitude);

                Map<String, String> m = new HashMap<>();
                m.put("topic", topicText);
                m.put("description", descriptionText);
                db.getReference("topicList").push().setValue(m);

                Log.d("", "button clicked");
                Intent i = new Intent(HostActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}


/*
events
    30298dsn
        dish: pasta
        cuisine: italian
        maxGuests: 3
        attendees:
            83sisfj39
            932n9fsn4

use datasnapshot.getValue(Event.class)
class Event {
    String dish;
    List<String> attendees
}
 */
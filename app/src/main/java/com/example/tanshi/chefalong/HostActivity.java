package com.example.tanshi.chefalong;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.location.Geocoder;
import android.widget.Toast;

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
    Button homeButton;
    FirebaseDatabase db;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    HashMap<String,String> emptyAttendees;

    private void addNewDishInfo(String host, String dish, String cuisine, int numGuests, String timeText, int duration, HashMap<String, String> attendees, double latitude, double longitude) {
        DishInfo event = new DishInfo(host, dish, cuisine, numGuests, timeText, duration, attendees, latitude, longitude);
        ref.child("events").push().setValue(event);
    }

    public LatLng getLocationFromAdress(String strAddress) {
        Toast.makeText(this, "trying to find "+strAddress, Toast.LENGTH_SHORT).show();
        Geocoder coder = new Geocoder(this);
        List<Address> addressList;
        LatLng p1 = null;

        try {
            addressList = coder.getFromLocationName(strAddress, 1);
            if (addressList == null) {
                return null;
            }
            Address location = addressList.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("", "Failed to geocode");
        }
        Toast.makeText(this, "latlng: "+p1, Toast.LENGTH_SHORT).show();
        return p1;
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
        homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(HostActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

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
                        Toast.makeText(HostActivity.this, "got addr from firebase:"+address, Toast.LENGTH_SHORT).show();
                        LatLng addressCoordinates = getLocationFromAdress(address);
                        double latitude = addressCoordinates.latitude;
                        double longitude = addressCoordinates.longitude;

                        addNewDishInfo(FirebaseAuth.getInstance().getCurrentUser().getEmail(), dishText.getText().toString(), cuisineText.getText().toString(), Integer.parseInt(numGuestsText.getText().toString()), timeText.getText().toString(), Integer.parseInt(durationText.getText().toString()), emptyAttendees, latitude, longitude);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //convert address to latitude and longitude with geocoding-- this is a placeholder:
                //double latitude = 190.2;
                //double longitude = 23.3;




                /*Geocoder gc = new Geocoder(context);
                if (gc.isPresent()) {
                    List<Address> list = gc.getFromLocationName(address, 1);
                    Address address = list.get(0);
                    latitude = address.getLatitude();
                    longitude = address.getLongitude();
                }*/



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
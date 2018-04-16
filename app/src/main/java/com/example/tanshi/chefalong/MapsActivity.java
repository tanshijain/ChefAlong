package com.example.tanshi.chefalong;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private GoogleMap mMap;
    HashMap<String, DishInfo> events = new HashMap<>();
    TextView dishNameTextView;
    String currentMarkerKey;
    Button joinButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        dishNameTextView = findViewById(R.id.dish_name);
        joinButton = findViewById(R.id.join_button);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMarkerKey == null)
                    return;
                DishInfo dishInfo = events.get(currentMarkerKey);
                if (dishInfo.attendees != null && dishInfo.numGuests == dishInfo.attendees.values().size()) {
                    Toast.makeText(MapsActivity.this, "This event is full.", Toast.LENGTH_SHORT);
                } else if(dishInfo.attendees.values().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Toast.makeText(MapsActivity.this, "You already joined this event.", Toast.LENGTH_SHORT);
                } else {
                    ref.child("events")
                            .child(currentMarkerKey)
                            .child("attendees")
                            .push()
                            .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sunnyvale and move the camera
        /*LatLng sunnyvale = new LatLng(37.3688, -122.0363);
        mMap.addMarker(new MarkerOptions().position(sunnyvale).title("Marker in Sunnyvale"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sunnyvale));*/
        mMap.setOnMarkerClickListener(this);
        ref.child("events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DishInfo dishInfo = dataSnapshot.getValue(DishInfo.class);
                double latitude = dishInfo.latitude;
                double longitude = dishInfo.longitude;
                LatLng latLng = new LatLng(latitude, longitude);
                Log.d("markerlatlng", latitude+" "+longitude);
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(dishInfo.dish));
                marker.setTag(dataSnapshot.getKey());
                Log.d("storingdishinfowithkey", dataSnapshot.getKey());
                events.put(dataSnapshot.getKey(), dishInfo);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DishInfo dishInfo = dataSnapshot.getValue(DishInfo.class);
                events.put(dataSnapshot.getKey(), dishInfo);
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        DishInfo dishInfo = events.get(marker.getTag());
        dishNameTextView.setText(dishInfo.dish);
        currentMarkerKey = (String)(marker.getTag());

        return false;
    }
}

package com.example.tanshi.chefalong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MapActivity extends AppCompatActivity {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    DishInfo dishInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //Button join = findViewById(R.id.joinButton);



        /*db.child("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dishInfo = dataSnapshot.getValue(DishInfo.class);
                List<String> attendees_list = (DishInfo.attendees);
                int guests = DishInfo.numGuests;
                if (attendees_list.size() == guests) {
                    Toast.makeText(MapActivity.this, "This event is filled up. No more sign-ups allowed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}

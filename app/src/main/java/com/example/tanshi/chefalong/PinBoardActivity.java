package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class PinBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_board);
        GridView gridView = findViewById(R.id.gridView);
        Button backButton = findViewById(R.id.backButton);
        gridView.setNumColumns(3);

        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PinBoardActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PinBoardActivity.this, ChosenPinActivity.class);
                startActivity(i);
                //finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(PinBoardActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}

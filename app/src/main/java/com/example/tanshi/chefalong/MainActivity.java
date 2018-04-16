package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = findViewById(R.id.SignUpButton);
        Button signInButton = findViewById(R.id.SignInButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

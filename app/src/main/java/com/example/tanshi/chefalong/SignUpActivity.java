package com.example.tanshi.chefalong;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    Button backButton;
    EditText newEmailField;
    EditText newPasswordField;
    EditText addressField;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();


    //You can add a user with setValue() as follows:
    private void addNewUserInfo(String userId, String email, String password, String address) {
        UserInfo user = new UserInfo(email, password, address);
        db.child("users").child(userId).setValue(user);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.signUpButton);
        newEmailField = findViewById(R.id.newEmail);
        newPasswordField = findViewById(R.id.newPassword);
        addressField = findViewById(R.id.address);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "button clicked");
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = newEmailField.getText().toString();
                String password = newPasswordField.getText().toString();
                if ((email.length() == 0) || (password.length() == 0)) {
                    Toast.makeText(SignUpActivity.this, "Please enter an email and password.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(FirebaseAuth.getInstance() == null)
                        Toast.makeText(SignUpActivity.this, "fbauthis nulk", Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("update", "createUserWithEmail:success");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Created account successfully.",
                                                Toast.LENGTH_SHORT).show();

                                        addNewUserInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(), newEmailField.getText().toString(), newPasswordField.getText().toString(), addressField.getText().toString());
                                        Log.d("update", "added new user:success");
                                        Intent i = new Intent(SignUpActivity.this, ProfileActivity.class);
                                        startActivity(i);
                                        Log.d("update", "started activity:success");
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Update", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }

            }
        });
    }
}

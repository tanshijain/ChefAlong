package com.example.tanshi.chefalong;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tanshi on 4/6/18.
 */

@IgnoreExtraProperties
public class UserInfo {

    public String email;
    public String password;
    public String address;

    //public User() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    //}

    public UserInfo() {}

    public UserInfo(String email, String password, String address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }

}

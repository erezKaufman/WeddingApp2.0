package com.example.erez0_000.weddingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) open correct activity for it.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            loggeduserUI();
        } else {
            anonymousUserUI();

        }
    }

    /**
     * start new anonymous-hello-screen activity
     */
    private void anonymousUserUI() {
        startActivity(new Intent(this, Anonymous_user_entry.class));
    }

    /**
     * start new LoggedUser-hello-screen activity
     */
    private void loggeduserUI() {

        startActivity(new Intent(this, Logged_user_entry.class));
    }
}
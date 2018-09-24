package com.example.erez0_000.weddingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Personal_window_Activity extends AppCompatActivity implements View.OnClickListener{

        private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_window);


//        // todo START add user image to personal window
//        FirebaseUser user = mAuth.getCurrentUser();
//        ImageView imageView = findViewById(R.id.imageView);
//        if (user.getPhotoUrl() != null){
//            imageView.setImageResource(user.getPhotoUrl());
//        }
        // todo END add user image to personal window

        // START config 'google sign in option' object
        // END config 'google sign in option' object

        findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_out_button:
                signout();
                break;
        }
    }

    private void signout() {
        // actuall FireBase sign out

        // change UI for user
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null); //TODO add UI change in here

                    }
                });

        finish();


    }
}

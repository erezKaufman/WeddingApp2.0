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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Personal_window extends AppCompatActivity implements View.OnClickListener{

        private FirebaseAuth mAuth;
        private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_window);

        mAuth = FirebaseAuth.getInstance();

//        // todo START add user image to personal window
//        FirebaseUser user = mAuth.getCurrentUser();
//        ImageView imageView = findViewById(R.id.imageView);
//        if (user.getPhotoUrl() != null){
//            imageView.setImageResource(user.getPhotoUrl());
//        }
        // todo END add user image to personal window

        // START config 'google sign in option' object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // END config 'google sign in option' object
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

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
        mAuth.signOut();

        // change UI for user
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null); //TODO add UI change in here

                    }
                });


    }
}

package com.example.erez0_000.weddingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity implements  View.OnClickListener {

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private static final int RC_SIGN_IN = 9001;
    private String TAG = "GoogleActivity";
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
//        findViewById(R.id.islogged).setOnClickListener(this);
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);

        // initialize auth
        mAuth = FirebaseAuth.getInstance();

        // START config 'google sign in option' object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // END config 'google sign in option' object

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

    }

    /**
     * each button arrives here and by switch case goes to each method
     * @param v view object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
//                OpenSignIn_and_info(v);
                Sign_in_and_info.start_info_activity(v.getContext());
                 break;
            case R.id.sign_out_button:
                signOut();
                break;
//            case R.id.islogged:
//                checkLogged();
//                break;
        }
    }

    /**
     * simple check if user is logged in
     */
    private void checkLogged() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mStatusTextView.setText("already logged in");
        }
    }

    /**
     * sign in method - called when user clicks on the sign in button. and we raise the google
     * sign in options, and then retrieve data from the user
     */
    private void signIn() {
        mAuth.signOut();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    /**
     * sign out user (NOT DELETING THE USER)
     */
    private void signOut() {
        // actuall FireBase sign out
        mAuth.signOut();

        // change UI for user
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);

                    }
                });


    }

    /**
     * simple update to the UI so the user knows what's happening. TODO - change this method
     * @param user
     */
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText("hello there, "+ user.getEmail());
            mDetailTextView.setText("your id is"+ user.getUid());

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText("you signed out");
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    /**
     * called when signin method calls 'startActivityForResult' - catches the result and handle it.
     * in here we log in the user
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.login_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void hideProgressDialog() {
        if (mprogressDialog != null && mprogressDialog.isShowing()){

            mprogressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mprogressDialog == null){
            mprogressDialog = new ProgressDialog(this);
            mprogressDialog.setCancelable(false);
            mprogressDialog.setMessage("אנא המתן בעת התחברות...");
        }
        mprogressDialog.show();
    }

    public void OpenSignIn_and_info(View view) {
        Sign_in_and_info.start_info_activity(view.getContext());
    }
}
/**
 * how to really delete the user from db
 */
//FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "User account deleted.");
//                }
//            }
//        });
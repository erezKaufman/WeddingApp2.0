package com.example.erez0_000.weddingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Sign_in_and_info extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean bool_age =false, bool_area=false, bool_type=false,
                    bool_number=false, bool_season=false, bool_cost=false;
    private EditText edit_age, edit_area, edit_type,
                     edit_vnumber, edit_season, edit_cost;

    private Button sign_in;
    private static final int RC_SIGN_IN = 9001;
    private String TAG = "GoogleActivity";
    private ProgressDialog mprogressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_and_info);

        // VIEW LOG IN BUTTON
        sign_in = findViewById(R.id.sign_in_button);
        sign_in.setOnClickListener(this);
        sign_in.setEnabled(false);

        // VIEW EDIT TEXTs
        edit_age = findViewById(R.id.editTextAge);


    }

    public static void start_info_activity(Context context) {
        context.startActivity(new Intent(context,Sign_in_and_info.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                signin();
                break;
        }
    }

    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
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
//                updateUI(null);//TODO change UI here
            }
        }
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
//                            updateUI(user); //TODO change UI here
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.login_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null); //TODO change UI here
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    private void showProgressDialog() {
        if (mprogressDialog == null){
            mprogressDialog = new ProgressDialog(this);
            mprogressDialog.setCancelable(false);
            mprogressDialog.setMessage("אנא המתן בעת התחברות...");
        }
        mprogressDialog.show();
    }

    private void hideProgressDialog() {
        if (mprogressDialog != null && mprogressDialog.isShowing()){

            mprogressDialog.dismiss();
        }
    }



//    ######################################## START override of TextWatcher ##################################
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        View v = getCurrentFocus();
        switch (v.getId()){
            case R.id.editTextAge:
                bool_age = true;
                break;
            case R.id.editTextCost:
                bool_age = true;
                break;
            case R.id.editTextNumber:
                bool_age = true;
                break;

        }

    }
//    ######################################## END override of TextWatcher ##################################


//    ######################################## START override of AdapterView.OnItemSelectedListener ##################################

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position >=1 ){
            switch (view.getId()){
                case R.id.area_spinner:
                    bool_area = true;
                    break;
                case R.id.season_spinner:
                    bool_season= true;
                    break;
                case R.id.type_spinner:
                    bool_type = true;
                    break;
            }
            if (bool_age && bool_area && bool_type && bool_number && bool_season && bool_cost){

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

//    ######################################## END override of AdapterView.OnItemSelectedListener ##################################
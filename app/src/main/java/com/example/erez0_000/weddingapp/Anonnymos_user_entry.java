package com.example.erez0_000.weddingapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
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


public class Anonnymos_user_entry extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

//    private TextView mStatusTextView;
//    private TextView mDetailTextView;


    private String TAG = "GoogleActivity";
    private ProgressDialog mprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonnymos_user_entry);

        // TODO add image of app's logo
        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
                null,this.getPackageName());
        weddingImage.setImageResource(imgResource);
        // initialize auth
        mAuth = FirebaseAuth.getInstance();

//        ############ TO CANCEL ##################
        findViewById(R.id.gotoSearch).setEnabled(false);// TODO CANCEL THIS!!!! AFTER Ofir's changes


    }


    /**
     * each button arrives here and by switch case goes to each method
     * @param v view object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotosignin:
                Sign_in_and_info.start_info_activity(v.getContext());
                break;
            case R.id.gotoSearch:
                // TODO add Ofir's search activity here
                break;

        }
    }




//    /**
//     * simple update to the UI so the user knows what's happening. TODO - change this method
//     * @param user
//     */
//    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText("hello there, "+ user.getEmail());
//            mDetailTextView.setText("your id is"+ user.getUid());
//
//            findViewById(R.id.startsign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText("you signed out");
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.startsign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
//        }
//    }



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

}

package com.example.erez0_000.weddingapp.Login_pages;
import android.app.ProgressDialog;
//import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;

import java.io.Serializable;


public class Anonymous_user_entry extends AppCompatActivity implements View.OnClickListener,
                                                                        Serializable {
    private Database database;
    private User newuser;
//    private TextView mStatusTextView;
//    private TextView mDetailTextView;

    private static final String TAG2 = "Anonymous_user_entry";

    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_user_entry);
        Log.d(TAG2, "onCreate: started Anonymous_user_entry");


        // TODO add image of app's logo
        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
                null,this.getPackageName());
        weddingImage.setImageResource(imgResource);

        database = Database.getInstance();

        findViewById(R.id.gotosignin).setOnClickListener(this);
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
                signin();
                // TODO make User object in class -then put all information on the user there. when
                // TODO the user is logged in - ask him to insert the favorite information and add
                // TODO it to the already ready node in the DB
//                finish();
//                Sign_in_and_info_Activity.start_info_activity(v.getContext(),newuser);
                break;
            case R.id.gotoSearch:
                // TODO add Ofir's search activity here
                break;

        }
    }

    private void signin() {
//        database
    }

//    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
//        Log.d(GOOGLETAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        showProgressDialog();
//
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, create a node with the user id and blank favorite
//                            // information.
//                            Log.d(GOOGLETAG, "signInWithCredential:success");
////                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            DatabaseReference userRef = FirebaseDatabase.getInstance()
//                                    .getReference("Users");
//                            Query query = mDatabase.child("Users").orderByChild("accountId")
//                                          .equalTo(acct.getId());
//                            query.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Log.d(TAGFDB, "onCancelled: read succeeded");
//                                    if (dataSnapshot.exists()){
//                                        // TODO MAKE DIALOG LINE LIKE "WELCOME BACK!"
//                                        newuser = dataSnapshot.getValue(User.class);
//                                        // TODO create a better query to retrive user information (take query from Ofir)
//                                        Anonymous_user_entry.this.finish();
//
//                                    }else{
//
//                                        String uEmail = mAuth.getCurrentUser().getEmail();
//                                        if (uEmail == null){
//                                            uEmail = "";
//                                        }
//                                        newuser = new User(uEmail,mAuth.getCurrentUser()
//                                                .getDisplayName(),
//                                                "","",
//                                                "","",
//                                                "","",acct.getId());
//
//                                        mDatabase.child("Users").child(newuser.getAccountId())
//                                                                .setValue(newuser);
//                                        Intent i = new Intent(Anonymous_user_entry.this
//                                                ,Sign_in_and_info_Activity.class);
//                                        i.putExtra("newUser",newuser);
//                                        startActivity(i);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    Log.d(TAGFDB, "onCancelled: read failed");
//                                }
//                            });
//
//
//
//
//
//
////                            updateUI(user); //TODO change UI here
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(GOOGLETAG, "signInWithCredential:failure", task.getException());
////                            Snackbar.make(findViewById(R.id.login_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
////                            updateUI(null); //TODO change UI here
//                        }
//
//                        // [START_EXCLUDE]
//                        hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
//    }


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

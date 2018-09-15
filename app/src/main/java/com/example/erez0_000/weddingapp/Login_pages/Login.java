package com.example.erez0_000.weddingapp.Login_pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.erez0_000.weddingapp.Personal_window;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.BusinessesActivity;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessList;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.example.erez0_000.weddingapp.searches.SearchActivity;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements  View.OnClickListener {


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;
    private static final String TAG = "Logged_user_entry";
    private static final String TAG2 = "Anonymous_user_entry";
    private ProgressDialog mprogressDialog;
    private User newuser;

    private static final int RC_SIGN_IN = 9001;

    private String GOOGLETAG = "GoogleActivity";
    private String TAGFDB = "firebaseDB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            loggeduserUI();
        } else {
            anonymousUserUI();

        }
    }
    //    @Override
//    public void onResume() {
//        super.onResume();
//        // Check if user is signed in (non-null) open correct activity for it.
////        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
////        if (currentUser != null) {
////            loggeduserUI();
////        } else {
////            anonymousUserUI();
////
////        }
//        startActivity(new Intent(this,SearchActivity.class));
//    }

    /**
     * start new anonymous-hello-screen activity
     */
    private void anonymousUserUI() {
//        startActivity(new Intent(this, Anonymous_user_entry.class));
        setContentView(R.layout.activity_anonymous_user_entry);
        Log.d(TAG2, "onCreate: started Anonymous_user_entry");


        // TODO add image of app's logo
        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
                null, this.getPackageName());
        weddingImage.setImageResource(imgResource);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // initialize auth
        mAuth = FirebaseAuth.getInstance();

        // START config 'google sign in option' object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // END config 'google sign in option' object
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        findViewById(R.id.gotosignin).setOnClickListener(this);
//        ############ TO CANCEL ##################
        findViewById(R.id.gotoSearch).setEnabled(false);// TODO CANCEL THIS!!!! AFTER Ofir's changes

    }

    /**
     * start new LoggedUser-hello-screen activity
     */
    private void loggeduserUI() {

//        startActivity(new Intent(this, Logged_user_entry.class));
        setContentView(R.layout.activity_logged_user_entry);
        Log.d(TAG, "onCreate: started Logged_user_entry");
        findViewById(R.id.gotoPersonalZone).setOnClickListener(this);
        findViewById(R.id.gotoSearch).setOnClickListener(this);
        findViewById(R.id.goto_categories).setOnClickListener(this);
        // TODO add image of app's logo
        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
                null, this.getPackageName());
        weddingImage.setImageResource(imgResource);

        // initialize auth
        mAuth = FirebaseAuth.getInstance();

//        ############ TO CANCEL ##################
        findViewById(R.id.gotoSearch).setEnabled(false);// TODO CANCEL THIS!!!! AFTER Ofir's changes

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // WHEN USER IS LOGGED: in case where the user chooses to go to 'personal zone' activity
            case R.id.gotoPersonalZone:
                startActivity(new Intent(this, Personal_window.class));
                break;
                // in case user chooses to go to 'search' activity
            case R.id.gotoSearch:
                // TODO add Ofir's search activity here
                break;
            // WHEN USER IS NOT LOGGED:  in case user chooses to go to 'search' activity
            case R.id.gotosignin:
                signin();
                break;
            // WHEN USER IS LOGGED: in case
            case R.id.goto_categories:
                startCategoryActivity();
                break;
        }
    }

    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    /**
     * simple
     */
    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                Log.w(GOOGLETAG, "Google sign in failed", e);
//                updateUI(null);//TODO change UI here
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(GOOGLETAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, create a node with the user id and blank favorite
                            // information.
                            Log.d(GOOGLETAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference userRef = FirebaseDatabase.getInstance()
                                    .getReference("Users");
                            Query query = mDatabase.child("Users").orderByChild("accountId")
                                    .equalTo(acct.getId());
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.d(TAGFDB, "onCancelled: read succeeded");
                                    if (dataSnapshot.exists()){
                                        // TODO MAKE DIALOG LINE LIKE "WELCOME BACK!"
                                        newuser = dataSnapshot.getValue(User.class);
                                        // TODO create a better query to retrive user information (take query from Ofir)
                                        finish();
                                        startActivity(getIntent());

                                    }else{

                                        String uEmail = mAuth.getCurrentUser().getEmail();
                                        if (uEmail == null){
                                            uEmail = "";
                                        }
                                        newuser = new User(uEmail,mAuth.getCurrentUser()
                                                .getDisplayName(),
                                                "","",
                                                "","",
                                                "","",acct.getId());

                                        mDatabase.child("Users").child(newuser.getAccountId())
                                                .setValue(newuser);
                                        Intent i = new Intent(Login.this
                                                ,Sign_in_and_info.class);
                                        i.putExtra("newUser",newuser);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d(TAGFDB, "onCancelled: read failed");
                                }
                            });






//                            updateUI(user); //TODO change UI here
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(GOOGLETAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.login_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null); //TODO change UI here
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
}
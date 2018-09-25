package com.example.erez0_000.weddingapp.Login_pages;

import com.example.erez0_000.weddingapp.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{



    private static final String TAG = "Logged_user_entry";
    private static final String TAG2 = "Anonymous_user_entry";
    private ProgressDialog mprogressDialog;

    private static final int RC_SIGN_IN = 9001;

    private String GOOGLETAG = "GoogleActivity";
    private String TAGFDB = "firebaseDB";
    private RecyclerView mResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        if (currentUser != null) {
//            Intent intent = new Intent(this,Logged_user_entry.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);
//            finish();
//        }
//        setContentView(R.layout.activity_anonymous_user_entry);
////        else {
////            Intent intent = new Intent(this,Anonymous_user_entry.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
////            startActivity(intent);
////        }
//
//        // TODO add image of app's logo
//        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
//        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
//                null,this.getPackageName());
//        weddingImage.setImageResource(imgResource);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        // initialize auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // START config 'google sign in option' object
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // END config 'google sign in option' object
//        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



        // TODO add image of app's logo
//        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
//        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
//                null,this.getPackageName());
//        weddingImage.setImageResource(imgResource);

        findViewById(R.id.gotosignup).setOnClickListener(this);
        findViewById(R.id.gotosignin).setOnClickListener(this);
        findViewById(R.id.gotoSearch).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.gotosignup:
                signup();
                break;
            // WHEN USER IS ANONYMOUS:  in case user chooses to go to 'search' activity
            case R.id.gotosignin:
                signin();
                break;
            // FOR LOGGED AND ANONYMOUS USERS: in case user chooses to go to 'search' activity
            case R.id.gotoSearch:
                openSearchActivity();
                break;
        }
    }
    //
    private void openSearchActivity() {
    }
//
//    private void addCalendarEvent() {
//        Calendar cal = Calendar.getInstance();
//        long startTime = cal.getTimeInMillis();
//        long endTime = cal.getTimeInMillis()  + 60 * 60 * 1000;
//        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
//        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
//        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
//        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
//        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
//        intent.setType("vnd.android.cursor.item/event");
//        startActivity(intent);
//    }
//
//    /**
//     *
//     */
//    private void startCategoryActivity() {
//        Intent intent = new Intent(this, CategoriesActivity.class);
//        startActivity(intent);
//    }
//

    private void signup() {

    }

    /**
     * FROM ANONYMOUS USER LAYOUT:
     * simple call for sign in by firebase auth. we call the signIn intent of google and receives
     * information back (after the user chooses a google user).
     * the rest is managed in 'onActivityResult'
     */
    private void signin() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


//    /**
//     * called when signin method calls 'startActivityForResult' - catches the result and handle it.
//     * in here we log in the user, change UI if needed to,
//     * and call 'firebaseAuthWithGoogle' method to handle the account
//     *
//     * @param requestCode request code from google sign in
//     * @param resultCode  result code from google sign in
//     * @param data        intent data brought from google sign in
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(GOOGLETAG, "Google sign in failed", e);
////                updateUI(null);//TODO change UI here
//            }
//        }
//    }
////
//    /**
//     * the method receives the account information that the user entered,
//     * and after log in was successful we create an empty user log in the db, and afterwards
//     * populate it with user information (if he wishes to fill them) in 'Sign_in_and_info_activity'
//     * if it's not the first log of the user, refresh the page with the proper layout
//     *
//     * @param acct GoogleSignInAccount account
//     */
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
//                                    .equalTo(acct.getId());
//                            query.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Log.d(TAGFDB, "onCancelled: read succeeded");
//                                    if (dataSnapshot.exists()) {
//                                        // TODO MAKE DIALOG LINE LIKE "WELCOME BACK!"
//                                        newuser = dataSnapshot.getValue(User.class);
//                                        // TODO create a better query to retrive user information (take query from Ofir)
//
//                                        Intent intent = new Intent(LoginActivity.this,Logged_user_entry.class);
////                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                        startActivity(intent);
//                                        finish();
//
//                                    } else {
//
//                                        String uEmail = mAuth.getCurrentUser().getEmail();
//                                        if (uEmail == null) {
//                                            uEmail = "";
//                                        }
//                                        newuser = new User(uEmail, mAuth.getCurrentUser()
//                                                .getDisplayName(),
//                                                "", "",
//                                                "", "",
//                                                "", "", acct.getId());
//
//                                        mDatabase.child("Users").child(newuser.getAccountId())
//                                                .setValue(newuser);
//                                        Intent i = new Intent(LoginActivity.this
//                                                , Sign_in_and_info_Activity.class);
//                                        i.putExtra("newUser", newuser);
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


    private void hideProgressDialog() {
        if (mprogressDialog != null && mprogressDialog.isShowing()) {

            mprogressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mprogressDialog == null) {
            mprogressDialog = new ProgressDialog(this);
            mprogressDialog.setCancelable(false);
            mprogressDialog.setMessage("אנא המתן בעת התחברות...");
        }
        mprogressDialog.show();
    }
}
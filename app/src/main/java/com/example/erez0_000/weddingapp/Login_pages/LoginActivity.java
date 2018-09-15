package com.example.erez0_000.weddingapp.Login_pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses.SuggestionRecyclerAdapter;
import com.example.erez0_000.weddingapp.Personal_window_Activity;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.StaticMethods;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.example.erez0_000.weddingapp.searches.SearchActivity;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;
import com.example.erez0_000.weddingapp.todos_section.TodoRecyclerViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


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
    private RecyclerView mResultList;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
//        findViewById(R.id.gotoSearch).setEnabled(false);// TODO CANCEL THIS!!!! AFTER Ofir's changes

//        initRecyclerView();
    }



    private void initRecyclerView() {
//        mUserDatabase = FirebaseDatabase.getInstance().getReference("Businesses");
////        mResultList = (RecyclerView) findViewById(R.id.suggestion_list);
////        mResultList.setHasFixedSize(true);
////        mResultList.setLayoutManager(new LinearLayoutManager(
////                this,LinearLayoutManager.HORIZONTAL,false));
//        Query firebaseSearchQuery = mUserDatabase.orderByChild("name");
//        FirebaseRecyclerOptions<Businesses> options = new FirebaseRecyclerOptions.Builder<Businesses>()
//                .setQuery(firebaseSearchQuery, Businesses.class).setLifecycleOwner(this).build();
//        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Businesses, SearchActivity.UsersViewHolder>(options) {
//            @Override
//            public SearchActivity.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_layout, parent, false);
//
//                return new SearchActivity.UsersViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull SearchActivity.UsersViewHolder holder, int position, @NonNull Businesses model) {
//                holder.setDetails(getApplicationContext(), model.getName(), model.getAddress(), model.getImage(),model.getRegion());
//
//            }
//        };
//        firebaseRecyclerAdapter.notifyDataSetChanged();
//        mResultList.setAdapter(firebaseRecyclerAdapter);



        mUserDatabase = FirebaseDatabase.getInstance().getReference("Businesses");
        mResultList = (RecyclerView) findViewById(R.id.suggestion_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(
                this));

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name");
        firebaseRecyclerAdapter = new SuggestionRecyclerAdapter(new FirebaseRecyclerOptions.Builder<Businesses>()
                .setQuery(firebaseSearchQuery, Businesses.class).setLifecycleOwner(this).build());

        firebaseRecyclerAdapter.notifyDataSetChanged();
        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // WHEN USER IS LOGGED: in case where the user chooses to go to 'personal zone' activity
            case R.id.gotoPersonalZone:
                startActivity(new Intent(this, Personal_window_Activity.class));
                break;
            // WHEN USER IS LOGGED: in case user chooses to go to 'category list' activity
            case R.id.goto_categories:
                startCategoryActivity();
                break;
            // WHEN USER IS ANONYMOUS:  in case user chooses to go to 'search' activity
            case R.id.gotosignin:
                signin();
                break;
            // FOR LOGGED AND ANONYMOUS USERS: in case user chooses to go to 'search' activity
            case R.id.gotoSearch:
                addCalendarEvent();
                // TODO add Ofir's search activity here
                break;
        }
    }

    private void addCalendarEvent() {
        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis()  + 60 * 60 * 1000;
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        intent.setType("vnd.android.cursor.item/event");
        startActivity(intent);
    }

    /**
     *
     */
    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    /**
     * FROM ANONYMOUS USER LAYOUT:
     * simple call for sign in by firebase auth. we call the signIn intent of google and receives
     * information back (after the user chooses a google user).
     * the rest is managed in 'onActivityResult'
     */
    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    /**
     * called when signin method calls 'startActivityForResult' - catches the result and handle it.
     * in here we log in the user, change UI if needed to,
     * and call 'firebaseAuthWithGoogle' method to handle the account
     *
     * @param requestCode request code from google sign in
     * @param resultCode  result code from google sign in
     * @param data        intent data brought from google sign in
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

    /**
     * the method receives the account information that the user entered,
     * and after log in was successful we create an empty user log in the db, and afterwards
     * populate it with user information (if he wishes to fill them) in 'Sign_in_and_info_activity'
     * if it's not the first log of the user, refresh the page with the proper layout
     *
     * @param acct GoogleSignInAccount account
     */
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(GOOGLETAG, "firebaseAuthWithGoogle:" + acct.getId());

        StaticMethods.showProgressDialog("אנא המתן בעת התחברות...",this);


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
                                    if (dataSnapshot.exists()) {
                                        // TODO MAKE DIALOG LINE LIKE "WELCOME BACK!"
                                        newuser = dataSnapshot.getValue(User.class);
                                        // TODO create a better query to retrive user information (take query from Ofir)
                                        finish();
                                        startActivity(getIntent());

                                    } else {

                                        String uEmail = mAuth.getCurrentUser().getEmail();
                                        if (uEmail == null) {
                                            uEmail = "";
                                        }
                                        newuser = new User(uEmail, mAuth.getCurrentUser()
                                                .getDisplayName(),
                                                "", "",
                                                "", "",
                                                "", "", acct.getId());

                                        mDatabase.child("Users").child(newuser.getAccountId())
                                                .setValue(newuser);
                                        Intent i = new Intent(LoginActivity.this
                                                , Sign_in_and_info_Activity.class);
                                        i.putExtra("newUser", newuser);
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
                        StaticMethods.hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


//    private void hideProgressDialog() {
//        if (mprogressDialog != null && mprogressDialog.isShowing()) {
//
//            mprogressDialog.dismiss();
//        }
//    }
//
//    private void showProgressDialog() {
//        if (mprogressDialog == null) {
//            mprogressDialog = new ProgressDialog(this);
//            mprogressDialog.setCancelable(false);
//            mprogressDialog.setMessage("אנא המתן בעת התחברות...");
//        }
//        mprogressDialog.show();
//    }
}
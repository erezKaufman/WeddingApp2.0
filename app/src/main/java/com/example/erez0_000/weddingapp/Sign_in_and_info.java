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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_in_and_info extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean bool_age =false, bool_area=false, bool_type=false,
                    bool_number=false, bool_season=false, bool_cost=false;
    private EditText edit_age,edit_number,  edit_cost;


    private Spinner edit_season,edit_area, edit_type;

    private String age_string, number_string, cost_string,
                    season_string, area_string, type_string;

    private SignInButton sign_in;
    private static final int RC_SIGN_IN = 9001;
    private String TAG = "GoogleActivity";

    private ProgressDialog mprogressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_and_info);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // START config 'google sign in option' object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // END config 'google sign in option' object
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        // VIEW LOG IN BUTTON
        sign_in = findViewById(R.id.sign_in_button);
        sign_in.setOnClickListener(this);
        sign_in.setEnabled(false);

        // START VIEW EDIT TEXTS
        edit_age = findViewById(R.id.editTextAge);
        edit_age.addTextChangedListener(this);

        edit_number = findViewById(R.id.editTextNumber);
        edit_number.addTextChangedListener(this);

        edit_cost = findViewById(R.id.editTextCost);
        edit_cost.addTextChangedListener(this);
        // END VIEW EDIT TEXTS


        // START VIEW Spinners
        edit_season = findViewById(R.id.season_spinner);
        edit_season.setOnItemSelectedListener(this);
        edit_area = findViewById(R.id.area_spinner);
        edit_area.setOnItemSelectedListener(this);
        edit_type = findViewById(R.id.type_spinner);
        edit_type.setOnItemSelectedListener(this);
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
                            User newUser = new User(user.getEmail(),user.getDisplayName(),
                                                    age_string,number_string,
                                                    type_string,cost_string,
                                                    area_string,season_string);
                            mDatabase.child("Users").child(user.getProviderId()).setValue(newUser);
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
        System.out.println("test before");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        System.out.println("test On");
    }

    @Override
    public void afterTextChanged(Editable s) {
        System.out.println("test after");
        View v = getCurrentFocus();
        System.out.println(s.toString());;
        if (s.toString().length() >=2) {
            switch (v.getId()) {
                case R.id.editTextAge:
                    bool_age = true;
                    age_string = s.toString();
                    break;
                case R.id.editTextCost:
                    bool_cost = true;
                    cost_string = s.toString();
                    break;
                case R.id.editTextNumber:
                    bool_number = true;
                    number_string = s.toString();
                    break;
                default:
                    System.out.println(v.getId());
            }
        }
        if (bool_age && bool_area && bool_type && bool_number && bool_season && bool_cost){
            sign_in.setEnabled(true);
        }

    }
//    ######################################## END override of TextWatcher ##################################


//    ######################################## START override of AdapterView.OnItemSelectedListener ##################################

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position >=1 ){
            switch (parent.getId()){
                case R.id.area_spinner:
                    bool_area = true;
                    area_string = parent.getSelectedItem().toString();
                    break;
                case R.id.season_spinner:
                    bool_season= true;
                    season_string = parent.getSelectedItem().toString();
                    break;
                case R.id.type_spinner:
                    bool_type = true;
                    type_string = parent.getSelectedItem().toString();
                    break;
                default:
                    System.out.println(view.getId());
            }
            if (bool_age && bool_area && bool_type && bool_number && bool_season && bool_cost){
                sign_in.setEnabled(true);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

//    ######################################## END override of AdapterView.OnItemSelectedListener ##################################
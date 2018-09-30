package com.example.erez0_000.weddingapp.Login_pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_in_and_info_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_age, edit_number, edit_cost;
    private ProgressDialog mprogressDialog;


    private Spinner edit_season, edit_area, edit_type;
    private Button backtomainButton, savechangesButton;
    private String TAG2 = "SignIn";


    User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_and_info);
        Log.d(TAG2, "onCreate: started Sign_in_and_info_Activity");

        newUser = User.thisUser;

        // START config 'google sign in option' object
        // END config 'google sign in option' object


        // START VIEW ON BUTTONS
        backtomainButton = findViewById(R.id.gobacktomain);
        backtomainButton.setOnClickListener(this);

        savechangesButton = findViewById(R.id.gotosignin);
        savechangesButton.setOnClickListener(this);
        // END VIEW ON BUTTONS


        // START VIEW EDIT TEXTS
        edit_age = findViewById(R.id.editTextAge);
//        edit_age.addTextChangedListener(this);

        edit_number = findViewById(R.id.editTextNumber);
//        edit_number.addTextChangedListener(this);

        edit_cost = findViewById(R.id.editTextCost);
//        edit_cost.addTextChangedListener(this);
        // END VIEW EDIT TEXTS

        // START VIEW Spinners
        edit_season = findViewById(R.id.season_spinner);
//        edit_season.setOnItemSelectedListener(this);
        edit_area = findViewById(R.id.area_spinner);
//        edit_area.setOnItemSelectedListener(this);
        edit_type = findViewById(R.id.type_spinner);
//        edit_type.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotosignin:
                savechanges();
                break;
            case R.id.gobacktomain:
                closeAndGoToLogged("אל דאגה, תוכלו לערוך את המידע מאוחר יותר דרך האיזור האישי");
                break;
        }
    }

    private void closeAndGoToLogged(String toastMessage) {
        Toast.makeText(Sign_in_and_info_Activity.this,
                toastMessage,Toast.LENGTH_LONG).show();
        Intent i = new Intent(Sign_in_and_info_Activity.this,Logged_user_entryActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        hideProgressDialog();
        finish();
    }

    /**
     * the method sends
     */
    private void savechanges() {
        showProgressDialog();
        newUser.setAge(edit_age.getText().toString());
        newUser.setArea(edit_area.getSelectedItem().toString());
        newUser.setCost(edit_cost.getText().toString());
        newUser.setNumber(edit_number.getText().toString());
        newUser.setSeason(edit_season.getSelectedItem().toString());
        newUser.setType(edit_type.getSelectedItem().toString());
        User.thisUser = newUser;
        Database db = Database.getInstance();
        db.updateUser(User.thisUser, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                closeAndGoToLogged("שינויים עודכנו בהצלחה");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(Sign_in_and_info_Activity.this,
                        "מתנצלים, יש כרגע בעיות התחברות עם השרת. אנא נסו מאוחר יותר",Toast.LENGTH_LONG).show();
            }
        });

    }


//
//
//    //    ######################################## START override of TextWatcher ##################################
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        System.out.println("test before");
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        System.out.println("test after");
//        View v = getCurrentFocus();
//        System.out.println(s.toString());
//        ;
//        if (s.toString().length() >= 2) {
//            switch (v.getId()) {
//                case R.id.editTextAge:
//                    bool_age = true;
//                    age_string = s.toString();
//                    break;
//                case R.id.editTextCost:
//                    bool_cost = true;
//                    cost_string = s.toString();
//                    break;
//                case R.id.editTextNumber:
//                    bool_number = true;
//                    number_string = s.toString();
//                    break;
//                default:
//                    System.out.println("couldnt find id " + v.getId());
//            }
//        }
//        if (bool_age && bool_area && bool_type && bool_number && bool_season && bool_cost) {
//        }
//
//    }
////    ######################################## END override of TextWatcher ##################################
//
//
////    ######################################## START override of AdapterView.OnItemSelectedListener ##################################
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if (position >= 1) {
//            switch (parent.getId()) {
//                case R.id.area_spinner:
//                    bool_area = true;
//                    area_string = parent.getSelectedItem().toString();
//                    break;
//                case R.id.season_spinner:
//                    bool_season = true;
//                    season_string = parent.getSelectedItem().toString();
//                    break;
//                case R.id.type_spinner:
//                    bool_type = true;
//                    type_string = parent.getSelectedItem().toString();
//                    break;
//                default:
//
//            }
//            if (bool_age && bool_area && bool_type && bool_number && bool_season && bool_cost) {
//            }
//        }
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
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
            mprogressDialog.setMessage("שומר נתונים אנא המתן...");
        }
        mprogressDialog.show();
    }
}

//    ######################################## END override of AdapterView.OnItemSelectedListener ##################################
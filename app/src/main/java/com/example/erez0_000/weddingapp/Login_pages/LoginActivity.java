package com.example.erez0_000.weddingapp.Login_pages;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sp;
    private Database db;

    private ProgressDialog mprogressDialog;
    private TextInputEditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        startActivity(new Intent(this, DisplayBusinessListActivity.class));

        sp = getSharedPreferences("pref", MODE_PRIVATE);
        final String userId = sp.getString("userId", null);
        db = Database.getInstance();

        if (userId != null) {
            showProgressDialog();
            db.signin(userId, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressDialog();
                    User.thisUser = response.body();
                    // TODO: Start new activity here
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Should not happen", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            });
        }
//        if (currentUser != null) {
//            Intent intent = new Intent(this,Logged_user_entryActivity.class);
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

        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
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
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, DisplayBusinessListActivity.class));
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

    private boolean areCredentialsEmpty() {
        return TextUtils.isEmpty(usernameEditText.getText().toString().trim()) ||
                TextUtils.isEmpty(passwordEditText.getText().toString().trim());
    }

    private void signup() {
        if (!areCredentialsEmpty()) {
            User.thisUser = new User();
            User.thisUser.setUsername(usernameEditText.getText().toString().trim());
            User.thisUser.setPassword(passwordEditText.getText().toString().trim());
            db.signup(User.thisUser, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User.thisUser = response.body();
                    sp.edit().putString("userId", User.thisUser.get_id()).apply();
                    // TODO: Start new activity here
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signin() {
        if (!areCredentialsEmpty()) {
            User.thisUser = new User();
            User.thisUser.setUsername(usernameEditText.getText().toString().trim());
            User.thisUser.setPassword(passwordEditText.getText().toString().trim());
            db.signin(User.thisUser.getUsername(), User.thisUser.getPassword(), new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User.thisUser = response.body();
                    sp.edit().putString("userId", User.thisUser.get_id()).apply();
                    // TODO: Start new activity here
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }


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
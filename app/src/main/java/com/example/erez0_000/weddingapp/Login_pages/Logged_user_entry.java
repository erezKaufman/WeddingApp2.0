package com.example.erez0_000.weddingapp.Login_pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.erez0_000.weddingapp.Personal_window_Activity;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessList;
import com.example.erez0_000.weddingapp.searches.SearchActivity;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;

public class Logged_user_entry extends AppCompatActivity implements View.OnClickListener    {
    private static final String TAG = "Logged_user_entry";
//    private FirebaseAuth mAuth;


    private ProgressDialog mprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        mAuth = FirebaseAuth.getInstance();

    }


    /**
     * each button arrives here and by switch case goes to each method
     * @param v view object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.gotoPersonalZone:
                startActivity(new Intent(this, Personal_window_Activity.class));
                break;
            case R.id.gotoSearch:
                openSearchActivity();
                break;
            case R.id.goto_categories:
                startCategoryActivity();
                break;
        }
    }

    /**
     *
     */
    private void openSearchActivity() {
//        Intent intent = new Intent(this,SearchActivity.class);
//        startActivity(intent);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, DisplayBusinessList.class));
    }
    /**
     *
     */
    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
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


    public void openBusinessChartFragment(View view) {

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
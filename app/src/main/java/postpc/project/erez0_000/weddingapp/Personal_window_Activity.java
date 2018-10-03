package postpc.project.erez0_000.weddingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import postpc.project.erez0_000.weddingapp.Login_pages.LoginActivity;
import postpc.project.erez0_000.weddingapp.Login_pages.Sign_in_and_info_Activity;
import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.db_classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import postpc.project.erez0_000.weddingapp.Login_pages.LoginActivity;
import postpc.project.erez0_000.weddingapp.Login_pages.Sign_in_and_info_Activity;
import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.db_classes.User;

public class Personal_window_Activity extends AppCompatActivity implements View.OnClickListener{

        private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_window);
        findViewById(R.id.editPersonalInfo).setOnClickListener(this);

//        // todo START add user image to personal window
//        FirebaseUser user = mAuth.getCurrentUser();
//        ImageView imageView = findViewById(R.id.imageView);
//        if (user.getPhotoUrl() != null){
//            imageView.setImageResource(user.getPhotoUrl());
//        }
        // todo END add user image to personal window

        // START config 'google sign in option' object
        // END config 'google sign in option' object

        findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_out_button:
                signout();
                break;
            case R.id.editPersonalInfo:
                editInfo();
                break;
        }
    }

    private void editInfo() {
        startActivity(new Intent(this, Sign_in_and_info_Activity.class));
    }

    private void signout() {

        // change UI for user
        Database db = Database.getInstance();
        // TODO: 30/09/2018 log the user out
        getSharedPreferences("pref", MODE_PRIVATE).edit().clear().apply();
        User.thisUser = null;
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();


    }
}

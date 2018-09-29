package com.example.erez0_000.weddingapp.Login_pages;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.example.erez0_000.weddingapp.todos_section.TodoList;

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

import java.util.ArrayList;

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
//                openSearchActivity();
                startActivity(new Intent(this,Logged_user_entryActivity.class));
                break;
        }
    }
    //
    private void openSearchActivity() {
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, DisplayBusinessListActivity.class));
    }


    private boolean areCredentialsEmpty() {
        return TextUtils.isEmpty(usernameEditText.getText().toString().trim()) ||
                TextUtils.isEmpty(passwordEditText.getText().toString().trim());
    }

    private void signup() {
        if (!areCredentialsEmpty()) {
            User.thisUser = new User();
            User.thisUser.setUsername(usernameEditText.getText().toString().trim());
            User.thisUser.setPassword(passwordEditText.getText().toString().trim());
            User.thisUser.setTodoArray(initTodoArray());

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
    private ArrayList<TodoList> initTodoArray() {
        ArrayList<TodoList> returnList = new ArrayList<>();
        TodoList eventTodo = new TodoList("אירוע");
        TodoList dressingTodo = new TodoList("הלבשה");
        TodoList helpingTodo = new TodoList("נקודות לעזור בתכנון החתונה");
        TodoList guestsTodo = new TodoList("אורחים");
        TodoList photoTodo = new TodoList("צילומים");
        TodoList extrasTodo = new TodoList("תוספות");
        TodoList honeyMoonTodo = new TodoList("ירח דבש");

        helpingTodo.addTodo("לקבוע תקציב");
            helpingTodo.addTaskInTodo(0,"אופי האירוע שנרצה");
            helpingTodo.addTaskInTodo(0,"מה העונה המועדפת שלנו?");
        helpingTodo.addTodo("לקבוע גודל אולם");
        helpingTodo.addTodo("לקבוע מספר מוזמנים");
            helpingTodo.addTaskInTodo(2,"מה מספר המוזמנים של החתן?");
            helpingTodo.addTaskInTodo(2,"מה מספר המוזמנים של הכלה?");
        helpingTodo.addTodo("לבחור מיקום עבור החתונה");
        helpingTodo.addTodo("לקבוע עם גורם מחתן");
        helpingTodo.addTodo("לכתוב נאום");

        eventTodo.addTodo("לבחור אולם");
        eventTodo.addTodo("לבחור קייטרינג");
            eventTodo.addTaskInTodo(1,"כשר? בשרי או חלבי?");
        eventTodo.addTodo("לבחור להקה");
            eventTodo.addTaskInTodo(2,"איזה סגנון מוזיקה?");
        eventTodo.addTodo("לבחור DJ");
            eventTodo.addTaskInTodo(3,"איזה סגנון מוזיקה?");
            eventTodo.addTaskInTodo(3,"עד איזו שעה?");
        eventTodo.addTodo("לשכור תאורה");

        guestsTodo.addTodo("עיצוב הזמנות");
        guestsTodo.addTodo("בחירת מספר מוזמנים");
        guestsTodo.addTaskInTodo(1,"מספר מוזמנים מצד החתן");
        guestsTodo.addTaskInTodo(1,"מספר מוזמנים מצד הכלה");
        guestsTodo.addTodo("סידור מקומות ישיבה");
        guestsTodo.addTodo("לשלוח הזמנות לחתונה");
        guestsTodo.addTaskInTodo(3,"לשלוח במייל או בדואר");
        guestsTodo.addTaskInTodo(3,"לוודא שכולם קיבלו את ההזמנות");

        photoTodo.addTodo("לבחור צלם");
        photoTodo.addTaskInTodo(0,"האם נרצה צלם סטילס?");
        photoTodo.addTaskInTodo(0,"האם נרצה צלם וידאו?");
        photoTodo.addTaskInTodo(0,"כמה צלמים?");
        photoTodo.addTodo("לעשות סט צילומים לחתונה");
        photoTodo.addTaskInTodo(1,"לבחור מיקום לצילומים");
        photoTodo.addTaskInTodo(1,"לבחור שעה לצילומים");

        dressingTodo.addTodo("לקנות שמלת כלה");
        dressingTodo.addTodo("לבחור שמלה לריקודים");
        dressingTodo.addTodo("לקנות חליפה לחתן");
        dressingTodo.addTodo("לבחור מאפרת");
        dressingTodo.addTodo("ללכת למאפרת");
        dressingTodo.addTodo("לבחור עיצוב שיער לאישה");
        dressingTodo.addTodo("ללכת לעיצוב שיער לאישה");
        dressingTodo.addTodo("לקבוע לעיצוב שיער לגבר");
        dressingTodo.addTodo("ללכת לעיצוב שיער לגבר");
        dressingTodo.addTodo("לקנות טבעות");
            dressingTodo.addTaskInTodo(9,"לעשות סבב התרשמות ראשוני");
            dressingTodo.addTaskInTodo(9,"לבצע מדידות");
            dressingTodo.addTaskInTodo(9,"איסוף סופי של הטבעות");



        extrasTodo.addTodo("לבחור זרים ליום החתונה");
        extrasTodo.addTodo("לשקול אם למצוא הפעלות לצעירים");
            extrasTodo.addTaskInTodo(1,"האם נרצה מנפח בלונים מקצועי?");
            extrasTodo.addTaskInTodo(1,"האם נרצה ליצן מקצועי?");
            extrasTodo.addTaskInTodo(1,"האם נרצה עמדת משחקי קונסולות?");

        honeyMoonTodo.addTodo("להזמין ירח דבש");

        returnList.add(eventTodo);
        returnList.add(dressingTodo);
        returnList.add(helpingTodo);
        returnList.add(guestsTodo);
        returnList.add(photoTodo);
        return returnList;
    }
}
package postpc.project.erez0_000.weddingapp.Login_pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import postpc.project.erez0_000.weddingapp.R;
import postpc.project.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.db_classes.User;
import postpc.project.erez0_000.weddingapp.todos_section.TodoList;

import java.util.ArrayList;

import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.todos_section.TodoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private Database db;

    private ProgressDialog mprogressDialog;
    private TextInputEditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(postpc.project.erez0_000.weddingapp.R.layout.activity_login);
        usernameEditText = findViewById(postpc.project.erez0_000.weddingapp.R.id.username_edittext);
        passwordEditText = findViewById(postpc.project.erez0_000.weddingapp.R.id.password_edittext);
        findViewById(postpc.project.erez0_000.weddingapp.R.id.gotosignup).setOnClickListener(this);
        findViewById(postpc.project.erez0_000.weddingapp.R.id.gotosignin).setOnClickListener(this);
        findViewById(postpc.project.erez0_000.weddingapp.R.id.gotoSearch).setOnClickListener(this);


        sp = getSharedPreferences("pref", MODE_PRIVATE);
        final String username = sp.getString("username", null),
                password = sp.getString("password", null);
        db = Database.getInstance();

        if (username != null && password != null) {
            showProgressDialog();
            db.signin(username, password, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressDialog();
                    User.thisUser = response.body();
                    goToNextActivity(Logged_user_entryActivity.class);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Should not happen", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            });
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case postpc.project.erez0_000.weddingapp.R.id.gotosignup:
                showProgressDialog();
                signup();
                hideProgressDialog();
                break;
            // WHEN USER IS ANONYMOUS:  in case user chooses to go to 'search' activity
            case postpc.project.erez0_000.weddingapp.R.id.gotosignin:
//                showProgressDialog();
                signin();
//                hideProgressDialog();
                break;
            // FOR LOGGED AND ANONYMOUS USERS: in case user chooses to go to 'search' activity
            case postpc.project.erez0_000.weddingapp.R.id.gotoSearch:
                openSearchActivity();
//                startActivity(new Intent(this,Logged_user_entryActivity.class));
                break;
        }
    }

    //
    private void openSearchActivity() {
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
            User.thisUser.setMinCurrentDestinedAmmount(0);
            User.thisUser.setMaxCurrentDestinedAmmount(0);
            User.thisUser.setCost("0");

            db.signup(User.thisUser, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User.thisUser = response.body();
                    sp.edit().putString("username", User.thisUser.getUsername())
                            .putString("password", User.thisUser.getPassword())
                            .apply();
                    goToNextActivity(Sign_in_and_info_Activity.class);
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


    private void goToNextActivity(Class<?> mClass ) {
        Intent i = new Intent(LoginActivity.this,mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
//        finish();
    }


    private void signin() {
        // TODO: 30/09/2018 handle unfamiliar user trying to sign in
        if (!areCredentialsEmpty()) {
            showProgressDialog();
            User.thisUser = new User();
            User.thisUser.setUsername(usernameEditText.getText().toString().trim());
            User.thisUser.setPassword(passwordEditText.getText().toString().trim());
            db.signin(User.thisUser.getUsername(), User.thisUser.getPassword(), new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressDialog();
                    if (response.body() == null){
                        Toast.makeText(LoginActivity.this,
                                "משתמש לא נמצא", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User.thisUser = response.body();
                    sp.edit().putString("username", User.thisUser.getUsername())
                            .putString("password", User.thisUser.getPassword())
                            .apply();

                    goToNextActivity(Logged_user_entryActivity.class);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
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
        TodoList photoTodo = new TodoList("צילום");
        TodoList extrasTodo = new TodoList("תוספות");
        TodoList honeyMoonTodo = new TodoList("ירח דבש");

        helpingTodo.addTodo("לקבוע תקציב");
        helpingTodo.addTaskInTodo(0, "אופי האירוע שנרצה");
        helpingTodo.addTaskInTodo(0, "מה העונה המועדפת שלנו?");
        helpingTodo.addTodo("לקבוע גודל אולם");
        helpingTodo.addTodo("לקבוע מספר מוזמנים");
        helpingTodo.addTaskInTodo(2, "מה מספר המוזמנים של החתן?");
        helpingTodo.addTaskInTodo(2, "מה מספר המוזמנים של הכלה?");
        helpingTodo.addTodo("לבחור מיקום עבור החתונה");
        helpingTodo.addTodo("לקבוע עם גורם מחתן");
        helpingTodo.addTodo("לכתוב נאום");

        eventTodo.addTodo("לבחור אולם");
        eventTodo.addTodo("לבחור קייטרינג");
        eventTodo.addTaskInTodo(1, "כשר? בשרי או חלבי?");
        eventTodo.addTodo("לבחור להקה");
        eventTodo.addTaskInTodo(2, "איזה סגנון מוזיקה?");
        eventTodo.addTodo("לבחור DJ");
        eventTodo.addTaskInTodo(3, "איזה סגנון מוזיקה?");
        eventTodo.addTaskInTodo(3, "עד איזו שעה?");
        eventTodo.addTodo("לשכור תאורה");

        guestsTodo.addTodo("עיצוב הזמנות");
        guestsTodo.addTodo("בחירת מספר מוזמנים");
        guestsTodo.addTaskInTodo(1, "מספר מוזמנים מצד החתן");
        guestsTodo.addTaskInTodo(1, "מספר מוזמנים מצד הכלה");
        guestsTodo.addTodo("סידור מקומות ישיבה");
        guestsTodo.addTodo("לשלוח הזמנות לחתונה");
        guestsTodo.addTaskInTodo(3, "לשלוח במייל או בדואר");
        guestsTodo.addTaskInTodo(3, "לוודא שכולם קיבלו את ההזמנות");

        photoTodo.addTodo("לבחור צלם");
        photoTodo.addTaskInTodo(0, "האם נרצה צלם סטילס?");
        photoTodo.addTaskInTodo(0, "האם נרצה צלם וידאו?");
        photoTodo.addTaskInTodo(0, "כמה צלמים?");
        photoTodo.addTodo("לעשות סט צילומים לחתונה");
        photoTodo.addTaskInTodo(1, "לבחור מיקום לצילומים");
        photoTodo.addTaskInTodo(1, "לבחור שעה לצילומים");

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
        dressingTodo.addTaskInTodo(9, "לעשות סבב התרשמות ראשוני");
        dressingTodo.addTaskInTodo(9, "לבצע מדידות");
        dressingTodo.addTaskInTodo(9, "איסוף סופי של הטבעות");


        extrasTodo.addTodo("לבחור זרים ליום החתונה");
        extrasTodo.addTodo("לשקול אם למצוא הפעלות לצעירים");
        extrasTodo.addTaskInTodo(1, "האם נרצה מנפח בלונים מקצועי?");
        extrasTodo.addTaskInTodo(1, "האם נרצה ליצן מקצועי?");
        extrasTodo.addTaskInTodo(1, "האם נרצה עמדת משחקי קונסולות?");

        honeyMoonTodo.addTodo("להזמין ירח דבש");

        returnList.add(eventTodo);
        returnList.add(dressingTodo);
        returnList.add(helpingTodo);
        returnList.add(guestsTodo);
        returnList.add(photoTodo);
        returnList.add(extrasTodo);
        returnList.add(honeyMoonTodo);
        returnList.add(new TodoList("המטלות שלי"));
        return returnList;
    }
//    private static MaterialShowcaseView create(Activity activity, View view, int content, String id, Integer radius)
//    {
//        MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(activity)
//                .setTarget(view)
//                //.setDismissText(button)
//                //.setDismissTextColor(Tools.getThemeColor(activity, R.attr.colorPrimary))
//                .setMaskColour(Color.argb(150, 0, 0, 0))
//                .setContentText(content)
//                .setDismissOnTouch(true)
//                .setDelay(0); // optional but starting animations immediately in onCreate can make them choppy
//
//        if (radius != null)
//        {
//            builder.setUseAutoRadius(false);
//            builder.setRadius(radius);
//        }
//        else
//            builder.setUseAutoRadius(true);
//
//        if (id != null)
//            builder.singleUse(id); // provide a unique ID used to ensure it is only shown once
//
//        MaterialShowcaseView showcaseView = builder.build();
//        return showcaseView;
//    }
}


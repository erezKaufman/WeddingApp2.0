package com.example.erez0_000.weddingapp.Login_pages;

import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.erez0_000.weddingapp.Personal_window_Activity;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.db_classes.User;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;
import com.example.erez0_000.weddingapp.todos_section.CategoriesActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import java.util.concurrent.ThreadLocalRandom;

public class Logged_user_entryActivity extends AppCompatActivity
        implements View.OnClickListener,
        BusinessChartFragment.OnUpdateCostsListener{
    private static final String TAG = "LoggedUsErentryActivity";
    private User loggedUser;
    private RecyclerView recyclerView;
    private ProgressDialog mprogressDialog;
    private List<Businesses> businessHorizontalList;
    private TextView curBalance,welcome_text;
    private LinearLayout currentBalanceLayout;
    private Button gotopZone;
    private static final String WELCOMETEXT = "ברוכים הבאים לאפליקציית תכנון החתונות! האפליקציה נועדה לעזור לכם למצוא בעלי מקצוע העוסקים בארגון," +
            " תכנון והכנה לאירוע המרגש שלכם, ולעשות סדר בכל הנוגע למטלות והכנות שצריך לעשות לקראת האירוע. המדריך נועד להראות לכם את האפשרויות ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_entry);
        Log.d(TAG, "onCreate: started Logged_user_entryActivity");
        gotopZone = findViewById(R.id.gotoPersonalZone);
        gotopZone.setOnClickListener(this);
        findViewById(R.id.gotoSearch).setOnClickListener(this);
        findViewById(R.id.goto_categories).setOnClickListener(this);
        findViewById(R.id.chartLinearLayout).setOnClickListener(this);
        welcome_text = findViewById(R.id.welcome_text);
        welcome_text.setText(String.format("שלום %s! ברוך שובך",User.thisUser.getUsername()));

        // TODO add image of app's logo
        ImageView weddingImage = (ImageView) findViewById(R.id.weedingHello);
        int imgResource = getResources().getIdentifier("@drawble/wedding planner30210",
                null, this.getPackageName());
        weddingImage.setImageResource(imgResource);
        businessHorizontalList = new ArrayList<>();
        recyclerView = findViewById(R.id.suggestion_list);
        curBalance = findViewById(R.id.balance);
        currentBalanceLayout = findViewById(R.id.chartLinearLayout);


       showManuel();
    }

    private void showManuel() {
//        new MaterialShowcaseView.Builder(this)
////                .setTarget(gotopZone)
//                .setDismissText("בואו נתחיל!")
//                .setContentText("This is some amazing feature you should know about")
//                .setDelay(10) // optional but starting animations immediately in onCreate can make them choppy
//
//                .show();
        // TODO: 02/10/2018 add and expand this
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, User.thisUser.getUsername());

        sequence.setConfig(config);

        sequence.addSequenceItem(null,
                WELCOMETEXT, "בואו נתחיל!");
//
//        sequence.addSequenceItem(startSearch_btn,
//                "כשתלחץ על הכפתור הזה, יתחיל החיפוש", "קיבלתי");

//        sequence.addSequenceItem(mButtonThree,
//                "This is button three", "GOT IT");
//
        sequence.start();


    }

    /**
     * the method takes information from the user singleton and updates the current balance to show
     * it to the user
     */
    public void updateCurrentBalance() {
        User curUser = User.thisUser;
        curBalance.setText(String.format("בין %s לבין %s",
                curUser.getMinCurrentDestinedAmmount(),
                curUser.getMaxCurrentDestinedAmmount()));
        int currentCost = Integer.parseInt(curUser.getCost());
        if (currentCost != 0) {
            if (currentCost >= curUser.getMinCurrentDestinedAmmount()) {
                if (currentCost <= curUser.getMaxCurrentDestinedAmmount()) {
                    // TODO: 30/09/2018 change color to yellow add warning text
                    currentBalanceLayout.setBackgroundColor(Color.YELLOW);
                    return;
                }
                currentBalanceLayout.setBackgroundColor(R.drawable.red_border);
                // TODO: 30/09/2018 change color to red and add warning text
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCurrentBalance();
        callForRandomBusinessList();
    }

    private void callForRandomBusinessList() {
        showProgressDialog();
        Database db = Database.getInstance();
        // TODO: 29/09/2018 need to have user with his preferences here

        Map<String,String> map = getRandomPreferencesMap();
        db.getBusinesses(map, new Callback<List<Businesses>>() {
            @Override
            public void onResponse(Call<List<Businesses>> call, Response<List<Businesses>> response) {
                hideProgressDialog();
                businessHorizontalList = response.body();
                initHorizontalRecyclerView(businessHorizontalList); // TODO: 30/09/2018 make sure this works after we populate the DB

            }

            @Override
            public void onFailure(Call<List<Businesses>> call, Throwable t) {
                hideProgressDialog();
                System.out.println(t.getCause());
            }
        });
    }

    private void initHorizontalRecyclerView(List<Businesses> businessList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, businessList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myadapter);

    }


    /**
     * each button arrives here and by switch case goes to each method
     *
     * @param v view object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.gotoPersonalZone:
                startActivity(new Intent(this, Personal_window_Activity.class));
                break;
            case R.id.gotoSearch:
                openSearchActivity();
                break;
            case R.id.goto_categories:
                startCategoryActivity();
                break;
            case R.id.chartLinearLayout:
                openBusinessChartFragment();
                break;
        }
    }

    /**
     *
     */
    private void openSearchActivity() {
        startActivity(new Intent(this, DisplayBusinessListActivity.class));
    }

    /**
     *
     */
    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
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
            mprogressDialog.setMessage("אנא המתן בעת טעינת הנתונים");
        }
        mprogressDialog.show();
    }


    public void openBusinessChartFragment() {
        // TODO: 28/09/2018 open from here new fragment that will hold all the business the user chose to work with.
        // TODO: 28/09/2018  in turn, the fragment will hold a recycler view of all the businesses. and you can open by clicking on it the business page (without the ability to order new appointment)
        android.app.FragmentManager fm = getFragmentManager();
        BusinessChartFragment businessChartFragment = BusinessChartFragment.newInstance();
        businessChartFragment.setListener(this);
//        businessChartFragment.insertBusinesses(loggedUser.getBusinessInChart());
        businessChartFragment.show(fm, null);

    }

    public Map<String, String> getRandomPreferencesMap() {
        Map<String,String> curMap = new HashMap<>();
        User curUser = User.thisUser;
            if (curUser.getArea()!= null && !curUser.getArea().isEmpty()){
            curMap.put("Region",curUser.getArea());
        }
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6);
        String businessType = getResources().getStringArray(R.array.Business_Type)[randomNum];
        curMap.put("Business_Type",businessType);

        return curMap;

    }

    @Override
    public void updateCost() {
        updateCurrentBalance();
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
package postpc.project.erez0_000.weddingapp.activities;

import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import postpc.project.erez0_000.weddingapp.db_classes.Database;
import postpc.project.erez0_000.weddingapp.db_classes.User;
import postpc.project.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;
import postpc.project.erez0_000.weddingapp.R;
import postpc.project.erez0_000.weddingapp.db_classes.Businesses;
import postpc.project.erez0_000.weddingapp.searches.FabFilterFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import postpc.project.erez0_000.weddingapp.db_classes.Database;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class DisplayBusinessListActivity extends AppCompatActivity
    implements View.OnClickListener, FabFilterFragment.FilterListener{

    private int regionInt,typeInt,kosherInt;
    private String regionStr,typeStr,kosherStr;
    private ProgressDialog mprogressDialog;
    private Button startSearch_btn;
    private FloatingActionButton fab ;
    private List<Businesses> lstBusinesses;
    private RecyclerView recyclerView ;
    private EditText businessNameSearch;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(postpc.project.erez0_000.weddingapp.R.layout.activity_display_business);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        lstBusinesses = new ArrayList<>() ;
        recyclerView = findViewById(postpc.project.erez0_000.weddingapp.R.id.result_list);

        fab = findViewById(postpc.project.erez0_000.weddingapp.R.id.filter_fab);
        fab.setOnClickListener(this);

        startSearch_btn = findViewById(postpc.project.erez0_000.weddingapp.R.id.startSearch_btn);
        startSearch_btn.setOnClickListener(this);
        businessNameSearch = findViewById(postpc.project.erez0_000.weddingapp.R.id.insertBusinessName);
        regionInt = 0;
        typeInt = 0;
        kosherInt = 0;
        regionStr = "";
        kosherStr = "";
        typeStr = "";

        // get DB instance
        db = Database.getInstance();

        getInfoFromDb();



    }

    /**
     * the method opens the guide animation in the user's first run in the page
     */
    private void showManuel() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, User.thisUser.getUsername()+"DISPLAY");

        sequence.setConfig(config);

        sequence.addSequenceItem(fab,
                "בלחיצה על הכפתור הבא, יפתח עמוד סינון התוצאות", "קיבלתי");

        sequence.addSequenceItem(startSearch_btn,
                "וכאן אפשר להתחיל את החיפוש", "קיבלתי");

//        sequence.addSequenceItem(mButtonThree,
//                "This is button three", "GOT IT");

        sequence.start();

        //        new MaterialShowcaseView.Builder(this)
//                .setTarget(startSearch_btn)
//                .setDismissText("הבנתי")
//                .setContentText("כשתלחץ על הכפתור הזה, יתחיל החיפוש")
//                .setDelay(10) // optional but starting animations immediately in onCreate can make them choppy
//                .singleUse("SINGLEUSE") // provide a unique ID used to ensure it is only shown once
//                .show();
//        new MaterialShowcaseView.Builder(this)
//                .setTarget(startSearch_btn)
//                .setDismissText("הבנתי")
//                .setContentText("כשתלחץ על הכפתור הזה, יתחיל החיפוש")
//                .setDelay(10) // optional but starting animations immediately in onCreate can make them choppy
//                .singleUse("SINGLEUSE") // provide a unique ID used to ensure it is only shown once
//                .show();
        // TODO: 02/10/2018 add and expand this
    }

    private void setuprecyclerview(List<Businesses> lstBusinesses) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstBusinesses) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case postpc.project.erez0_000.weddingapp.R.id.filter_fab:
                openFilter();
                break;
            case postpc.project.erez0_000.weddingapp.R.id.startSearch_btn:
                getInfoFromDb();
                break;
        }
    }

    /**
     * the method is called after clicking the fab filter button. it opens a dialog fragment of
     * spinners, representing the filters for the businesses search. after the dialog is closed the
     * app does a @GET request from the DB and posts the information in the recyclerView
     */
    private void openFilter() {
        FragmentManager ft = getFragmentManager();
        FabFilterFragment fabFragment = FabFilterFragment.newInstance();
        fabFragment.setListener(this);
        fabFragment.setSpinnerVals(typeInt,regionInt,kosherInt);
        fabFragment.show(ft ,null);

    }

    @Override
    public void addFilters(String type, int typeIndex, String region, int regionIndex,
                           String kosher, int kosherIndex) {
        typeInt = typeIndex;
        regionInt = regionIndex;
        kosherInt = kosherIndex;
        typeStr = type;
        regionStr = region;
        kosherStr = kosher;
        getInfoFromDb();
    }

    public void getInfoFromDb() {
        showProgressDialog();
        Map<String,String> map  = fillMap();
        // TODO: 28/09/2018  the map filter doesn't work. need to talk with Ben about it
        updateBusinessList(map);
    }

    private void updateBusinessList(Map<String, String> map) {
        db.getBusinesses(map, new Callback<List<Businesses>>() {
            @Override
            public void onResponse(Call<List<Businesses>> call, Response<List<Businesses>> response) {
                hideProgressDialog();
                lstBusinesses = response.body();
                setuprecyclerview(lstBusinesses);
                showManuel();

            }

            @Override
            public void onFailure(Call<List<Businesses>> call, Throwable t) {
                hideProgressDialog();
                System.out.println(t.getCause());
                Toast.makeText(DisplayBusinessListActivity.this,
                        "מתנצלים, יש כרגע בעיות התחברות עם השרת. אנא נסו מאוחר יותר",Toast.LENGTH_LONG).show();
            }
        });
    }

    private Map<String, String> fillMap() {
        Map<String,String> emptyMap  = Collections.emptyMap();
        Map<String,String> map  = new HashMap<>();

        if (!regionStr.isEmpty()){
            map.put("Region",regionStr);
        }
        if (!typeStr.isEmpty()){
            map.put("Business_Type",typeStr);
        }
        if (!kosherStr.isEmpty()){
            if (kosherStr.equals("חלבי")){
                map.put("MilkKosher",kosherStr);
            }else {
                map.put("MeatKosher",kosherStr);
            }
        }
        if (!businessNameSearch.getText().toString().isEmpty()){
            map.put("Name",businessNameSearch.getText().toString());
        }
        if (map.size() ==0){
            return emptyMap;
        }
        return map;
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
            mprogressDialog.setMessage("יוצר רשימת עסקים...");
        }
        mprogressDialog.show();
    }
}

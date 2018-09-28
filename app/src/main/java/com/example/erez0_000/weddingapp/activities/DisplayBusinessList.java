package  com.example.erez0_000.weddingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.searches.FabFilterFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayBusinessList extends AppCompatActivity
    implements View.OnClickListener, FabFilterFragment.FilterListener{

    // this url is from my github - a raw json file with the Buisnaess class data members
    private int regionInt,typeInt,kosherInt;
    private String regionStr,typeStr,kosherStr;

    private List<Businesses> lstBusinesses;
    private RecyclerView recyclerView ;
    private EditText businessNameSearch;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_business);

        lstBusinesses = new ArrayList<>() ;
        recyclerView = findViewById(R.id.result_list);
        findViewById(R.id.filter_fab).setOnClickListener(this);
        findViewById(R.id.startSearch_btn).setOnClickListener(this);
        businessNameSearch = findViewById(R.id.insertBusinessName);
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

//    private void jsonrequest() {
//
//        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                JSONObject jsonObject  = null ;
//
//                for (int i = 0 ; i < response.length(); i++ ) {
//
//
//                    try {
//                        jsonObject = response.getJSONObject(i) ;
//                        Businesses businesses = new Businesses() ;
//
//                        businesses.setName(jsonObject.getString("name"));
//                        businesses.setDescription(jsonObject.getString("description"));
//                        businesses.setAddress(jsonObject.getString("address"));
//                        businesses.setMail(jsonObject.getString("mail"));
//                        businesses.setPhone(jsonObject.getInt("phone"));
//                        businesses.setRegion(jsonObject.getString("region"));
//                        businesses.setImage(jsonObject.getString("img"));
//
//                        businesses.setBid(jsonObject.getInt("bid"));
//                        businesses.setWinter_price(jsonObject.getInt("winter_price"));
//                        businesses.setSummer_price(jsonObject.getInt("summer_price"));
//                        businesses.setBusiness_type(jsonObject.getInt("business_type"));
//                        businesses.setKosher(jsonObject.getString("handikaped"));
//                        businesses.setKosher(jsonObject.getString("kosher"));
//
//                        lstBusinesses.add(businesses);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//                setuprecyclerview(lstBusinesses);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//
//        requestQueue = Volley.newRequestQueue(DisplayBusinessList.this);
//        requestQueue.add(request) ;
//
//
//    }

    private void setuprecyclerview(List<Businesses> lstBusinesses) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstBusinesses) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_fab:
                openFilter();
                break;
            case R.id.startSearch_btn:
                break;
        }
    }

    /**
     * the method is called after clicking the fab filter button. it opens a dialog fragment of
     * spinners, representing the filters for the businesses search. after the dialog is closed the
     * app does a @GET request from the DB and posts the information in the recyclerView
     */
    private void openFilter() {
        FragmentManager ft = getSupportFragmentManager();
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
        Map<String,String> map  = fillMap();

        db.getBusinesses(map, new Callback<List<Businesses>>() {
            @Override
            public void onResponse(Call<List<Businesses>> call, Response<List<Businesses>> response) {
                lstBusinesses = response.body();
                setuprecyclerview(lstBusinesses);

            }

            @Override
            public void onFailure(Call<List<Businesses>> call, Throwable t) {
//                Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();
                System.out.println(t.getCause());
                Toast.makeText(DisplayBusinessList.this,
                        "מתנצלים, יש כרגע בעיות התחברות עם השרת. אנא נסו מאוחר יותר",Toast.LENGTH_LONG).show();
            }
        });
    }

    private Map<String, String> fillMap() {
        Map<String,String> emptyMap  = Collections.emptyMap();
        Map<String,String> map  = new HashMap<String, String>();

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
            businessNameSearch.getText().clear();
        }
        if (map.size() ==0){
            return emptyMap;
        }
        return map;
    }
}

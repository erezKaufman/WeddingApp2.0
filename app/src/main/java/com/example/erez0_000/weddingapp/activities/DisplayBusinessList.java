package  com.example.erez0_000.weddingapp.activities;

import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;


//import com.example.erez0_000.weddingapp.Businesses;
import com.example.erez0_000.weddingapp.db_classes.Database;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.searches.SearchActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayBusinessList extends AppCompatActivity {

    // this url is from my github - a raw json file with the Buisnaess class data members
    private final String JSON_URL = "https://raw.githubusercontent.com/SimonMira/ex4_PostPC/master/jsonParsePage.json" ;
//    private JsonArrayRequest request ;
//    private RequestQueue requestQueue ;
    private List<Businesses> lstBusinesses;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_business);

        lstBusinesses = new ArrayList<>() ;
        recyclerView = findViewById(R.id.result_list);
//        jsonrequest();

        Database db = Database.getInstance();
        Map<String,String> map = Collections.emptyMap();

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
}

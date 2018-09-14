//package com.demotxt.myapp.parseJSON.activities;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.demotxt.myapp.parseJSON.R;
//import com.demotxt.myapp.parseJSON.adapters.RecyclerViewAdapter;
//import com.demotxt.myapp.parseJSON.model.Businesses;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    // this url is from my github - a raw json file with the Buisnaess class data members
//    private final String JSON_URL = "https://raw.githubusercontent.com/SimonMira/ex4_PostPC/master/jsonParsePage.json" ;
//    private JsonArrayRequest request ;
//    private RequestQueue requestQueue ;
//    private List<Businesses> lstBusinesses;
//    private RecyclerView recyclerView ;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        lstBusinesses = new ArrayList<>() ;
//        recyclerView = findViewById(R.id.recyclerviewid);
//        jsonrequest();
//
//
//
//    }
//
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
//                        businesses.setKosher(jsonObject.getBoolean("handikaped"));
//                        businesses.setKosher(jsonObject.getBoolean("kosher"));
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
//        requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(request) ;
//
//
//    }
//
//    private void setuprecyclerview(List<Businesses> lstBusinesses) {
//
//
//        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstBusinesses) ;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myadapter);
//
//    }
//}

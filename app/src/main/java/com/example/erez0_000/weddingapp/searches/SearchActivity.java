package com.example.erez0_000.weddingapp.searches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{


    // some search bar btns and spinners..
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private Spinner mRegionSpinner;
    private Spinner mTypeSpinner;
    private Spinner mKosherSpinner;



    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

         // free search buttons and fields

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Businesses");
        mSearchField = findViewById(R.id.search_field);
        mSearchBtn = findViewById(R.id.search_btn);



        //////handle  region spinner - start
        mRegionSpinner = findViewById(R.id.region_spinner);

        List<String> regionList = new ArrayList<>();
        regionList.add("צפון");
        regionList.add("מרכז");
        regionList.add("דרום");

        ArrayAdapter<String> regionSpinAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, regionList);
        regionSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mRegionSpinner.setAdapter(regionSpinAdapter);

        mRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String regionVal = parent.getItemAtPosition(position).toString();
                searchByRegion(regionVal);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //////handle  region spinner - finish


        //////handle  type spinner - start


        mTypeSpinner = (Spinner) findViewById(R.id.type_spinner);

        List<String> typeList = new ArrayList<>();
        typeList.add("גן אירועים");
        typeList.add("אולם אירועים");

        ArrayAdapter<String> typeSpinAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeList);
        typeSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTypeSpinner.setAdapter(typeSpinAdapter);

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeVal = parent.getItemAtPosition(position).toString();
                searchByType(typeVal);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //////handle  type spinner - finish





        //////handle  kosher spinner - start

        mKosherSpinner = (Spinner) findViewById(R.id.kosher_spinner);

        List<String> kosherList = new ArrayList<>();
        kosherList.add("כשר");
        kosherList.add("לא כשר");

        ArrayAdapter<String> kosherSpinAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, kosherList);
        kosherSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mKosherSpinner.setAdapter(kosherSpinAdapter);

        mKosherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kosherVal = parent.getItemAtPosition(position).toString();
                searchByKosher(kosherVal);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //////handle  kosher spinner - finish



        ///this code block handles the display of the free search bar
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                searchByBusinessName(searchText);

            }
        });



    }


    /////////// Searching Methods/////////////////////////

    private void searchByBusinessName(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        createRecyclerCall(firebaseSearchQuery);

    }




    private void searchByRegion(String reg) {
        Query firebaseSearchQuery;
        if(reg.equals("דרום")){
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("south").endAt("south" + "\uf8ff");
        } else if(reg.equals("מרכז")){
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("center").endAt("center" + "\uf8ff");

        }else{
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("north").endAt("north" + "\uf8ff");
        }

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();


        createRecyclerCall(firebaseSearchQuery);
    }



    private void searchByType(String type) {
        Query firebaseSearchQuery;
        if(type.equals("גן אירועים")){
            firebaseSearchQuery = mUserDatabase.orderByChild("type").startAt("garden").endAt("garden" + "\uf8ff");

        }else{
            firebaseSearchQuery = mUserDatabase.orderByChild("type").startAt("closed hall").endAt("closed hall" + "\uf8ff");
        }

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        // this funck is a black magic:
        createRecyclerCall(firebaseSearchQuery);
    }

    private void searchByKosher(String kosher) {
        Query firebaseSearchQuery;
        if(kosher.equals("לא כשר")){
            firebaseSearchQuery = mUserDatabase.orderByChild("kosher").startAt("false").endAt("false" + "\uf8ff");


        }else{
            firebaseSearchQuery = mUserDatabase.orderByChild("kosher").startAt("true").endAt("true" + "\uf8ff");

        }

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        createRecyclerCall(firebaseSearchQuery);
    }




    //////////////////////////////////////////////////////////

    private void createRecyclerCall(Query firebaseSearchQuery) {
        FirebaseRecyclerOptions<Businesses> options = new FirebaseRecyclerOptions.Builder<Businesses>()
                .setQuery(firebaseSearchQuery, Businesses.class).setLifecycleOwner(this).build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Businesses, UsersViewHolder>(options) {
            @Override
            public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);

                return new UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Businesses model) {
                holder.setDetails(getApplicationContext(), model.getName(), model.getAddress(), model.getImage(),model.getRegion());

            }
        };
        firebaseRecyclerAdapter.notifyDataSetChanged();
        mResultList.setAdapter(firebaseRecyclerAdapter);
    }


    // View Holder Class



    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userAddress, String userImage,
                               String userRegion){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_address = (TextView) mView.findViewById(R.id.status_text);
            TextView user_region = (TextView) mView.findViewById(R.id.region_text);

            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);

            // this will make sure the current obj details will appear
            user_name.setText(userName);
            user_address.setText(userAddress);
            user_region.setText(userRegion);

            Glide.with(ctx).load(userImage).into(user_image);

        }
    }

}

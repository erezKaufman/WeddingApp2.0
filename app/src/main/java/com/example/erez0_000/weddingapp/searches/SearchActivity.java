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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class SearchActivity extends AppCompatActivity{

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private Button mSouthBtn;
    private Button mCenterBtn;
    private Button mNorthBtn;



    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Businesses");
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);


        ///// find south region

        mSouthBtn = (Button) findViewById(R.id.cat_btn);
        mSouthBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchByRegion("south");
            }
        });

        ///// find in center region

        mCenterBtn = (Button) findViewById(R.id.cntBtn);
        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchByRegion("center");
            }
        });

        ///// find in north region
        mNorthBtn = (Button) findViewById(R.id.northBtn);
        mNorthBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchByRegion("north");
            }
        });


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

    private void searchByBusinessName(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        createRecyclerCall(firebaseSearchQuery);

    }

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


    private void searchByRegion(String reg) {
        Query firebaseSearchQuery;
        if(reg.equals("south")){
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("south").endAt("south" + "\uf8ff");
        } else if(reg.equals("center")){
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("center").endAt("center" + "\uf8ff");

        }else{
            firebaseSearchQuery = mUserDatabase.orderByChild("region").startAt("north").endAt("north" + "\uf8ff");
        }

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();


        createRecyclerCall(firebaseSearchQuery);
    }




    //////////////////////////////////////////////////////////


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

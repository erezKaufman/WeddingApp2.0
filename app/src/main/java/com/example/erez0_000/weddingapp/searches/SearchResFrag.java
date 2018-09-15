package com.example.erez0_000.weddingapp.searches;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.erez0_000.weddingapp.Businesses;
import com.example.erez0_000.weddingapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class SearchResFrag extends DialogFragment {
    EditText editText;
    ResultFragmentListner listner;
    View layout;
    Button send_button;
    int groupPosition;
    private Query query;

    private RecyclerView curRecyclerView;
    private FirebaseRecyclerOptions<Businesses> options;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    public void setListner(ResultFragmentListner dlgFrag) {
        listner = dlgFrag;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_results_frag,container, false);
        layout = view.findViewById(R.id.search_result_id);
//        editText = view.findViewById(R.id.sub_task_editText);
//        send_button = view.findViewById(R.id.sub_task_btn);
//        send_button.setOnClickListener(this);
        curRecyclerView = view.findViewById(R.id.result_list);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        options = new FirebaseRecyclerOptions.Builder<Businesses>().setQuery(query, Businesses.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Businesses, SearchMainActivity.UsersViewHolder>(options) {
            @Override
            public SearchMainActivity.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);

                return new SearchMainActivity.UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SearchMainActivity.UsersViewHolder holder, int position, @NonNull Businesses model) {
                holder.setDetails(getActivity().getApplicationContext(), model.getName(), model.getAddress(), model.getImage(), model.getRegion());

            }
        };
        firebaseRecyclerAdapter.startListening();

        curRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
        dismiss();
    }

    public interface ResultFragmentListner {
        void onDialogAddClick(String message, int groupPos);
    }
}


